var express = require('express');
var fs = require('fs');
var request = require('request');
var cheerio = require('cheerio');
var app     = express();

app.get('/', function(req, res){
  res.json("Ola Mundo");
});

app.listen('8080')
console.log('Magic happens on port 8080');
