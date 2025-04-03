//Finalmente, crea el archivo principal app.js:
const express = require('express');
const bodyParser = require('body-parser');
const cors = require('cors');
const pdfRoutes = require('./ruta/ruta.js');
const db = require('./configuracion/db.js');

const app = express();
const PORT = process.env.PORT || 3000;

// Middleware
app.use(cors());
app.use(bodyParser.json());

// Rutas
app.use('/api/pdf', pdfRoutes);

// Manejo de errores
app.use((err, req, res, next) => {
    console.error(err.stack);
    res.status(500).json({ error: 'Algo salió mal!' });
});

// Iniciar servidor
app.listen(PORT, () => {
    console.log("Servidor corriendo en http://localhost:${PORT}");
});

// Cerrar conexión a la DB al terminar
process.on('SIGINT', async () => {
    await db.end();
    process.exit();
});