var port    = 8080;
var express = require('express');
var request = require('request');
var cheerio = require('cheerio');
var fs      = require('fs');
var app     = express();
var birds   = require('./routes/api');

app.use('/api', birds);

app.listen(port);
console.log('http://localhost:' + port);
