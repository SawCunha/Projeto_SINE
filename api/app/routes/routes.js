'use strict';

module.exports = (app) => {
  const vagasController = app.controllers.vaga;

  app.get('/', (req, res) => {
      res.send('API SINE - TP ANDROID');
    });

  app.get('/vaga/:cidade/:funcao/:vaga', vagasController.getVaga);

  // vagas?idfuncao={idfuncao}&idcidade={idcidade}&numPagina={numPage}&tipoOrdenacao={ordenacao}
  app.get('/vagas', vagasController.getVagas);

  app.get('/idfuncao/:funcao', vagasController.getIdFuncoes);

  app.get('/idcidade/:cidade', vagasController.getIdCidades);
};
