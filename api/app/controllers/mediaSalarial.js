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

      MediaSalarial.detalhesFuncao = {};
      MediaSalarial.detalhesFuncao.salarios = {};

      MediaSalarial.detalhesFuncao.salarios.pequenaEmpresa = {};
      MediaSalarial.detalhesFuncao.salarios.pequenaEmpresa.trainee = objDetalhes.SalarioPequena.Trainee;
      MediaSalarial.detalhesFuncao.salarios.pequenaEmpresa.junior = objDetalhes.SalarioPequena.Junior;
      MediaSalarial.detalhesFuncao.salarios.pequenaEmpresa.pleno = objDetalhes.SalarioPequena.Pleno;
      MediaSalarial.detalhesFuncao.salarios.pequenaEmpresa.senior = objDetalhes.SalarioPequena.Senior;
      MediaSalarial.detalhesFuncao.salarios.pequenaEmpresa.master = objDetalhes.SalarioPequena.Master;

      MediaSalarial.detalhesFuncao.salarios.mediaEmpresa = {};
      MediaSalarial.detalhesFuncao.salarios.mediaEmpresa.trainee = objDetalhes.SalarioMedia.Trainee;
      MediaSalarial.detalhesFuncao.salarios.mediaEmpresa.junior = objDetalhes.SalarioMedia.Junior;
      MediaSalarial.detalhesFuncao.salarios.mediaEmpresa.pleno = objDetalhes.SalarioMedia.Pleno;
      MediaSalarial.detalhesFuncao.salarios.mediaEmpresa.senior = objDetalhes.SalarioMedia.Senior;
      MediaSalarial.detalhesFuncao.salarios.mediaEmpresa.master = objDetalhes.SalarioMedia.Master;

      MediaSalarial.detalhesFuncao.salarios.grandeEmpresa = {};
      MediaSalarial.detalhesFuncao.salarios.grandeEmpresa.trainee = objDetalhes.SalarioGrande.Trainee;
      MediaSalarial.detalhesFuncao.salarios.grandeEmpresa.junior = objDetalhes.SalarioGrande.Junior;
      MediaSalarial.detalhesFuncao.salarios.grandeEmpresa.pleno = objDetalhes.SalarioGrande.Pleno;
      MediaSalarial.detalhesFuncao.salarios.grandeEmpresa.senior = objDetalhes.SalarioGrande.Senior;
      MediaSalarial.detalhesFuncao.salarios.grandeEmpresa.master = objDetalhes.SalarioGrande.Master;

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