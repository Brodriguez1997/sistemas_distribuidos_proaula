//Crea el modelo models/pdfModel.js:
const db = require('../configuracion/db.js');

class PdfModel {
    static async getAll() {
        const res = await db.query('SELECT * FROM archivo');
        return res.rows;
    }

    static async getById(id) {  
        const res = await db.query('SELECT * FROM archivo WHERE id = $1', [id]);
        return res.rows[0];     
    }

    static async create(nombre, nodo, peso) {
        const fechaActual = new Date();
        const res = await db.query(
            'INSERT INTO archivo(nombre, nodo, peso, fecha) VALUES($1, $2, $3, $4) RETURNING *',
            [nombre, nodo, peso, fechaActual]
        );
        return res.rows[0];
    }

    static async update(id, nombre, nodo, peso) {
        const res = await db.query(
            'UPDATE archivo SET nombre = $1, ruta = $2, tamaño = $3 WHERE id = $4 RETURNING',
            [nombre, nodo, peso, id]
        );
        return res.rows[0];
    }

    static async delete(id) {
        const res = await db.query('DELETE FROM archivos WHERE id = $1 RETURNING *', [id]);
        return res.rows[0];
    }
}

module.exports = PdfModel;