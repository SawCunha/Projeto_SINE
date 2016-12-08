'use strict';

var http = require('http');
var request = require('request');
var app = require('./config/express')();

http.createServer(app).listen(app.get('port'), function(){
    console.log('Express Server in ' + app.get('port'));
});