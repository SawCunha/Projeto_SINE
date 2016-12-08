'use strict';

module.exports = function(app){
    app.get('/', function (req, res) {
        res.send('API SINE RAIZ');
    });

    const controller = app.controllers.vaga;
    app.get('/vaga', controller.getVaga);
}