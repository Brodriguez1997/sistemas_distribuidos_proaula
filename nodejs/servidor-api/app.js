const express = require('express');
const bodyParser = require('body-parser');
const cors = require('cors');
const pdfRoutes = require('./ruta/ruta.js');
const db = require('./configuracion/db.js');

const app = express();
const PORT = 3000;

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

// Verificar conexión a la base de datos al iniciar
db.query('SELECT NOW()')
    .then(() => console.log('Conexión a PostgreSQL verificada'))
    .catch(err => console.error('Error al conectar con PostgreSQL:', err));

// Iniciar servidor
const server = app.listen(PORT, () => {
    console.log(`Servidor corriendo en http://localhost:${PORT}`);
});

// Cerrar conexiones adecuadamente
process.on('SIGTERM', () => {
    console.log('Apagando servidor...');
    server.close(() => {
        db.end();
        console.log('Servidor apagado');
        process.exit(0);
    });
});

process.on('SIGINT', () => {
    server.close(() => {
        db.end();
        process.exit(0);
    });
});