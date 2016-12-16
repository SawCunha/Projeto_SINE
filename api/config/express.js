'use strict';

var express = require('express');
var expressLoad = require('express-load');
var bodyParser = require('body-parser');
var methodOverride = require('method-override');
var cors = require('cors')



const Cidade = require('../app/models/cidade');
const constants = require('../app/resources/constants');

module.exports = function () {
  var app = express();
  app.use(cors());
  app.set('port', constants.SERVER.PORT);

  // let c = new Cidade('Acrelândia','AC');
  // console.log(c);

  app.use(bodyParser.urlencoded({ extended: true }));
  app.use(bodyParser.json({ limit: '5mb' }));
  app.use(methodOverride());

  app.use(function(req, res, next) {
    for (var key in req.query)
    {
      if(key !== key.toLowerCase()){
        req.query[key.toLowerCase()] = req.query[key];
        delete req.query[key];
      }
    }
    next();
  });

  expressLoad('controllers', { cwd: 'app' })
  .then('routes')
  .into(app);

  return app;
};
