const { Client } = require('pg');

const client = new Client({
    host: 'localhost',
    port: 5432,
    user: 'postgres',
    password: 'Antoniors1997',
    database: 'convertidor_pdf',
});

client.connect()
    .then(() => console.log("Conectado a PostgreSQL"))
    .catch(err => console.error("Error de conexión", err.stack));

module.exports = client;
