const soapService = require('../services/soapService.js');

// En tu controlador de login
const loginUser = async (req, res) => {
  try {
    const { username, password } = req.body;
    
    if (!username || !password) {
      return res.status(400).json({
        success: false,
        message: 'Username and password are required'
      });
    }

    const response = await soapService.callSoapMethod('loginSoap', {
      username,
      password
    }, {
      timeout: 10000
    });

    if (!response?.access) {
      throw new Error('Invalid response structure from SOAP service');
    }

    res.json({
      success: true,
      token: response.access
    });
  } catch (error) {
    console.error('Login failed:', error.message);
    
    const statusCode = error.message.includes('SOAP service') ? 502 : 500;
    
    res.status(statusCode).json({
      success: false,
      message: 'Authentication failed',
      detail: process.env.NODE_ENV === 'development' ? error.message : undefined
    });
  }
};

const registerUser = async (req, res, next) => {
  try {
    const userData = req.body;
    const response = await soapService.callSoapMethod('resgisterSoap', userData);
    
    res.status(201).json({
      success: true,
      user: response
    });
  } catch (error) {
    next(error);
  }
};

module.exports = { loginUser, registerUser };