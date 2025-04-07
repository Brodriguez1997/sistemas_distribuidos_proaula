const soap = require('soap');
const { parseString } = require('xml2js');
require('dotenv').config();

class SoapService {
  constructor() {
    this.client = null;
  }

  async initialize() {
    try {
      this.client = await soap.createClientAsync(process.env.SOAP_WSDL_URL, {
        endpoint: process.env.SOAP_ENDPOINT
      });
      console.log('SOAP client initialized');
    } catch (error) {
      console.error('SOAP initialization error:', error);
      throw error;
    }
  }

  async callSoapMethod(method, args) {
    if (!this.client) await this.initialize();
    
    return new Promise((resolve, reject) => {
      this.client[method](args, (err, result) => {
        if (err) return reject(err);
        
        // Convertir la respuesta XML a JSON
        parseString(result[`${method}Result`], (parseErr, parsed) => {
          if (parseErr) return reject(parseErr);
          resolve(parsed);
        });
      });
    });
  }
}

module.exports = new SoapService();