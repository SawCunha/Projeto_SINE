'use strict';

module.exports = (app) => {
  const vagasController = app.controllers.vaga;

  app.get('/', (req, res) => {
      res.send('API SINE - TP ANDROID');
    });

  app.get('/vaga/:cidade/:funcao?/:vaga?', (req, res) => {
    const cidade = req.params.cidade;
    const funcao = req.params.funcao;
    const vaga = req.params.vaga;
    if (cidade && funcao && vaga)
      vagasController.getVaga(req, res, cidade, funcao, vaga);
    else {
      res.json('Listagem de vagas... Em construção');
    }
  });
};
