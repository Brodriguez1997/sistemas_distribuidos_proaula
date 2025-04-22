//Crea el modelo models/pdfModel.js:
const db = require('../configuracion/db.js');

class PdfModel {
    static async getAll() {
        const res = await db.query('SELECT * FROM archivos_pdf');
        return res.rows;
    }

    static async getById(id) {
        const res = await db.query('SELECT * FROM archivos_pdf WHERE id = $1', [id]);
        return res.rows[0];
    }

    static async create(nombre, ruta, tamaño) {
        const fechaActual = new Date();
        const res = await db.query(
            'INSERT INTO archivo(nombre, nodo, peso, fecha) VALUES($1, $2, $3, $4) RETURNING *',
            [nombre, ruta, tamaño, fechaActual]
        );
        return res.rows[0];
    }

    static async update(id, nombre, ruta, tamaño) {
        const res = await db.query(
            'UPDATE archivos_pdf SET nombre = $1, ruta = $2, tamaño = $3 WHERE id = $4 RETURNING',
            [nombre, ruta, tamaño, id]
        );
        return res.rows[0];
    }

    static async delete(id) {
        const res = await db.query('DELETE FROM archivos_pdf WHERE id = $1 RETURNING *', [id]);
        return res.rows[0];
    }
}

module.exports = PdfModel;