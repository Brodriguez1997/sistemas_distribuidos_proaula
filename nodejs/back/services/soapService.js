const soap = require('soap');
const { parseString } = require('xml2js');
const https = require('https');
const http = require('http');
require('dotenv').config();

class SoapService {
  constructor() {
    this.client = null;
    this.lastRawResponse = null;
    this.initializationPromise = null;
  }

  async initialize() {
    if (this.initializationPromise) {
      return this.initializationPromise;
    }

    this.initializationPromise = new Promise(async (resolve, reject) => {
      try {
        if (!process.env.SOAP_WSDL_URL) {
          throw new Error('SOAP_WSDL_URL is not defined in environment variables');
        }

        const wsdlOptions = {
          endpoint: process.env.SOAP_ENDPOINT,
          forceSoap12Headers: true,
          escapeXML: false,
          request: this.createCustomRequest()
        };

        this.client = await soap.createClientAsync(process.env.SOAP_WSDL_URL, wsdlOptions);
        console.log('✅ SOAP client initialized successfully');
        resolve(this.client);
      } catch (error) {
        console.error('❌ SOAP initialization error:', error);
        this.initializationPromise = null;
        reject(new Error(`Failed to initialize SOAP client: ${error.message}`));
      }
    });

    return this.initializationPromise;
  }

  createCustomRequest() {
    return (options, callback) => {
      try {
        // Verificación segura de la URL
        const url = options.uri || options.url;
        if (!url) {
          throw new Error('No URL provided in SOAP request options');
        }

        const transport = url.startsWith('https') ? https : http;
        
        const req = transport.request(options, (res) => {
          let data = '';
          res.on('data', (chunk) => data += chunk);
          res.on('end', () => {
            this.lastRawResponse = data;
            callback(null, res, data);
          });
        });

        req.on('error', (err) => {
          callback(err);
        });

        if (options.body) {
          req.write(options.body);
        }
        req.end();
      } catch (error) {
        callback(error);
      }
    };
  }

  async callSoapMethod(method, args = {}, options = {}) {
    try {
      if (!this.client) {
        await this.initialize();
      }

      this.lastRawResponse = null;
      
      const result = await new Promise((resolve, reject) => {
        if (!this.client[method]) {
          return reject(new Error(`SOAP method ${method} not found`));
        }

        this.client[method](args, (err, result, raw, soapHeader) => {
          if (err) return reject(err);
          resolve({ result, raw, soapHeader });
        });
      });

      const xmlResponse = this.lastRawResponse || result.raw;
      
      if (!xmlResponse) {
        throw new Error('Empty response from SOAP service');
      }

      const cleanXml = this.cleanXmlResponse(xmlResponse);
      return this.parseXmlResponse(cleanXml, method);
      
    } catch (error) {
      console.error(`SOAP call failed for method ${method}:`, {
        error: error.message,
        stack: error.stack,
        rawResponse: this.lastRawResponse ? this.lastRawResponse.substring(0, 500) + '...' : null
      });
      throw error;
    }
  }

  cleanXmlResponse(xml) {
    if (!xml || typeof xml !== 'string') {
      return '';
    }

    const startTags = ['<?xml', '<soap', '<SOAP'];
    const startIndex = Math.min(
      ...startTags.map(tag => {
        const idx = xml.indexOf(tag);
        return idx >= 0 ? idx : Infinity;
      })
    );

    return startIndex !== Infinity ? xml.slice(startIndex) : xml;
  }

  parseXmlResponse(xml, method) {
    return new Promise((resolve, reject) => {
      if (!xml || typeof xml !== 'string') {
        return reject(new Error('Invalid XML input'));
      }

      parseString(xml, { 
        explicitArray: false, 
        trim: true,
        tagNameProcessors: [name => name.replace(/^.*:/, '')] // Elimina namespaces
      }, (err, result) => {
        if (err) {
          return reject(new Error(`XML parse error: ${err.message}`));
        }

        try {
          // Manejo de diferentes estructuras SOAP
          const envelope = result.Envelope || result.SOAPENV_Envelope;
          if (!envelope) {
            throw new Error('No SOAP Envelope found in response');
          }

          const body = envelope.Body || envelope.body;
          if (!body) {
            throw new Error('No SOAP Body found in response');
          }

          const responseKey = Object.keys(body).find(key => 
            key.includes(`${method}Response`) || 
            key.includes(`${method}Result`)
          );

          if (!responseKey) {
            throw new Error(`No response found for method ${method}`);
          }

          const responseData = body[responseKey];
          const resultKey = Object.keys(responseData).find(key => 
            key.includes(`${method}Result`) || 
            key === 'return'
          );

          const resultData = resultKey ? responseData[resultKey] : responseData;

          // Parsear JSON si es necesario
          if (typeof resultData === 'string' && resultData.trim().startsWith('{')) {
            try {
              resolve(JSON.parse(resultData));
            } catch (jsonError) {
              throw new Error(`Failed to parse JSON from SOAP response: ${jsonError.message}`);
            }
          } else {
            resolve(resultData);
          }
        } catch (parseError) {
          reject(parseError);
        }
      });
    });
  }
}

module.exports = new SoapService();