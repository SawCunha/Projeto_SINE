'use strict';

module.exports = (app) => {
  const vagasController = app.controllers.vaga;

  app.get('/', (req, res) => {
      res.send('API SINE - TP ANDROID');
    });

  app.get('/vaga/:cidade/:funcao?/:vaga?', (req, res) => {
    let cidade = req.params.cidade;
    let funcao = req.params.funcao;
    let vaga = req.params.vaga;

    vagasController.getVaga(req, res, cidade, funcao, vaga);
  });
};
