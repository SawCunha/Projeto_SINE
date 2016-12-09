'use strict';

module.exports = (app) => {
  const vagasController = app.controllers.vaga;

  app.get('/', (req, res) => {
      res.send('API SINE - TP ANDROID');
    });

  app.get('/vaga/:cidade/:funcao/:vaga', vagasController.getVaga);

  // vagas?funcao={funcao}&local={local}
  // local = cidade/estado
  app.get('/vagas', vagasController.getVagas);
};
