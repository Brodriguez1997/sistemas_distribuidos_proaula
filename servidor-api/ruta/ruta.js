const express = require('express');
const router = express.Router();
const PdfController = require('../controladores/controller.js');

router.get('/', PdfController.getAll);
router.get('/:id', PdfController.getById);
router.post('/', PdfController.create);
router.put('/:id', PdfController.update);
router.delete('/:id', PdfController.delete);

module.exports = router;
