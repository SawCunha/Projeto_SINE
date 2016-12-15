'use strict';

const rp = require('request-promise');
const utils = require('../resources/utils');
const modelMediaSalarial = require('../models/mediaSalarial');

module.exports = function(app){
  let controller = {};

  controller.getMediaSalarial = (req, res) => {
    const idFuncao = req.query.idfuncao;

    if (!idFuncao) {
      res.status(400).json('Faltando Parametro(s).');
      return;
    }

    const url = utils.urlGetMediaSalarial(idFuncao);

    rp(url)
    .then((body) => {
      let obj;

      try{
        obj = JSON.parse(body);
      }catch(err){
        console.error(err);
        res.status(200).json({'error':1});
      }

      let MediaSalarial = new modelMediaSalarial(obj);

      MediaSalarial.nomeFuncao = obj.NomeFuncao;
      MediaSalarial.descricaoFuncao = obj.DescricaoFuncao;

      const objDetalhes = obj.DetalhesFuncao;

      MediaSalarial.salarios = {};

      MediaSalarial.salarios.pequenaEmpresa = {};
      MediaSalarial.salarios.pequenaEmpresa.trainee = objDetalhes.SalarioPequena.Trainee;
      MediaSalarial.salarios.pequenaEmpresa.junior = objDetalhes.SalarioPequena.Junior;
      MediaSalarial.salarios.pequenaEmpresa.pleno = objDetalhes.SalarioPequena.Pleno;
      MediaSalarial.salarios.pequenaEmpresa.senior = objDetalhes.SalarioPequena.Senior;
      MediaSalarial.salarios.pequenaEmpresa.master = objDetalhes.SalarioPequena.Master;

      MediaSalarial.salarios.mediaEmpresa = {};
      MediaSalarial.salarios.mediaEmpresa.trainee = objDetalhes.SalarioMedia.Trainee;
      MediaSalarial.salarios.mediaEmpresa.junior = objDetalhes.SalarioMedia.Junior;
      MediaSalarial.salarios.mediaEmpresa.pleno = objDetalhes.SalarioMedia.Pleno;
      MediaSalarial.salarios.mediaEmpresa.senior = objDetalhes.SalarioMedia.Senior;
      MediaSalarial.salarios.mediaEmpresa.master = objDetalhes.SalarioMedia.Master;

      MediaSalarial.salarios.grandeEmpresa = {};
      MediaSalarial.salarios.grandeEmpresa.trainee = objDetalhes.SalarioGrande.Trainee;
      MediaSalarial.salarios.grandeEmpresa.junior = objDetalhes.SalarioGrande.Junior;
      MediaSalarial.salarios.grandeEmpresa.pleno = objDetalhes.SalarioGrande.Pleno;
      MediaSalarial.salarios.grandeEmpresa.senior = objDetalhes.SalarioGrande.Senior;
      MediaSalarial.salarios.grandeEmpresa.master = objDetalhes.SalarioGrande.Master;

      let json = {'error': 0, 'MediaSalarial': MediaSalarial};
      utils.keysToUnderscoreCase(json);

      res.status(200).json(json);

    })
    .catch((error) => {
          console.error(error);
          res.status(404).json({'mensagem':'Erro ao obter dados.'});
      });
  }

  return controller;
}