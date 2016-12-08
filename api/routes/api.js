var express = require('express');
var router = express.Router();

// interceptador, se necessario.
router.use(function timeLog(req, res, next) {
  console.log('Time: ', Date.now());
  next();
});

// raiz api
router.get('/', function (req, res) {
  res.send('API RAIZ');
});

module.exports = router;
