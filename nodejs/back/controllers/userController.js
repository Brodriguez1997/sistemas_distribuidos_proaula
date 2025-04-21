const soapService = require('../services/soapService.js');

const loginUser = async (req, res, next) => {
  try {
    const { username, password } = req.body;
    const response = await soapService.callSoapMethod('loginSoap', {
      username,
      password
    });
    
    res.json({
      success: true,
      token: response // Asumiendo que el SOAP devuelve un token
    });
  } catch (error) {
    next(error);
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