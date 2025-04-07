require('dotenv').config();
const express = require('express');
const cors = require('cors');
const bodyParser = require('body-parser');
const userRoutes = require('./routes/userRoutes');
const errorHandler = require('./middlewares/errorHandler');

const app = express();
const PORT = process.env.PORT || 3000;

// Middlewares
app.use(cors());
app.use(bodyParser.json());

// Rutas
app.use('/api/users', userRoutes);

// Manejo de errores
app.use(errorHandler);

app.listen(PORT, () => {
  console.log(`REST API running on http://localhost:${PORT}`);
  console.log(`Endpoints:`);
  console.log(`- POST /api/users/login`);
  console.log(`- POST /api/users/register`);
});