const PdfModel = require('../modelo/modelo.js');

const PdfController = {
    getAll: async (req, res) => {
        try {
            const archivos = await PdfModel.getAll();
            res.json(archivos);
        } catch (error) {
            res.status(500).json({ error: error.message });
        }
    },

    getById: async (req, res) => {
        try {
            const archivo = await PdfModel.getById(req.params.id);
            if (!archivo) {
                return res.status(404).json({ message: 'Archivo no encontrado' });
            }
            res.json(archivo);
        } catch (error) {
            res.status(500).json({ error: error.message });
        }
    },

    create: async (req, res) => {
        try {
            const { nombre, ruta, tama単o } = req.body;
            const nuevoArchivo = await PdfModel.create(nombre, ruta, tama単o);
            res.status(201).json(nuevoArchivo);
        } catch (error) {
            res.status(500).json({ error: error.message });
        }
    },

    update: async (req, res) => {
        try {
            const { nombre, ruta, tama単o } = req.body;
            const archivoActualizado = await PdfModel.update(
                req.params.id,
                nombre,
                ruta,
                tama単o
            );
            if (!archivoActualizado) {
                return res.status(404).json({ message: 'Archivo no encontrado' });
            }
            res.json(archivoActualizado);
        } catch (error) {
            res.status(500).json({ error: error.message });
        }
    },

    delete: async (req, res) => {
        try {
            const archivoEliminado = await PdfModel.delete(req.params.id);
            if (!archivoEliminado) {
                return res.status(404).json({ message: 'Archivo no encontrado' });
            }
            res.json({ message: 'Archivo eliminado correctamente' });
        } catch (error) {
            res.status(500).json({ error: error.message });
        }
    }
};

module.exports = PdfController;