const errorHandler = (err, req, res, next) => {
    console.error('Error:', err);
    
    // Manejar errores específicos de SOAP
    if (err.root && err.root.Envelope) {
      const fault = err.root.Envelope.Body[0].Fault[0];
      return res.status(500).json({
        error: 'SOAP Fault',
        faultCode: fault.faultcode[0],
        faultString: fault.faultstring[0]
      });
    }
  
    res.status(500).json({
      error: err.message || 'Internal Server Error'
    });
  };
  
  module.exports = errorHandler;