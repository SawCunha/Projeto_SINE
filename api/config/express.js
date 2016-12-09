'use strict';

var express = require('express');
var expressLoad = require('express-load');
var bodyParser = require('body-parser');
var methodOverride = require('method-override');
const Cidade = require('../app/models/cidade');
const constants = require('../app/resources/constants');

module.exports = function () {
  var app = express();
  app.set('port', constants.SERVER.PORT);

  // let c = new Cidade('Acrelândia','AC');
  // console.log(c);

  app.use(bodyParser.urlencoded({ extended: true }));
  app.use(bodyParser.json({ limit: '5mb' }));
  app.use(methodOverride());

  expressLoad('controllers', { cwd: 'app' })
  .then('routes')
  .into(app);

  return app;
};
