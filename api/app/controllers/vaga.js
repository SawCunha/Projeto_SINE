'use strict';
const Vaga = require('../models/vaga');
const request = require('request-promise');
const cheerio = require('cheerio');
const utils = require('../resources/utils');

module.exports = function (app) {
  let controller = {};

  controller.getVaga = function (req, res, cidade, funcao, id) {
    let vaga = new Vaga({ id: id, cidade: cidade, funcao: funcao });
    vaga = this.createVaga(req, res, vaga);
  };

  controller.createVaga = (req, res, vaga) => {
    let url = vaga.url_sine;
    request(url, function (error, response, html) {
      if (!error) {
        let $ = cheerio.load(html);
        vaga.descricao = cleanString($('dd[itemprop="description"]').text());
        vaga.cidade = cleanString($('.label_cidade_resultado').text());
        vaga.empresa = cleanString($('.label_empresa_resultado').text());
        vaga.titulo = cleanString($('h1[itemprop="title"]').text());
        vaga.telefone = cleanString($('#ctl00_cphConteudo_ddTelefone').text());
        try{
          vaga.salario = cleanString($('#ctl00_cphConteudo_hplMediaSalarial').get(0).prev.data);
        } catch(err){}

        res.status(200).json(vaga);
      }
    });
  };

  controller.getVagas = (req, res) => {
    const funcao = req.query.funcao;
    const local = req.query.local;

    if(!funcao && !local){
      res.status(400).json('Faltando Parametro(s).');
      return;
    }

    const url = utils.urlSearch(funcao, local);

    request(url)
    .then((html) => {
      let $ = cheerio.load(html);

      //Pegar a lista de vagas
    })
    .catch((error) => {
      console.error(error);
      res.status(404).json('Erro ao obter dados.')
    });
  }

  return controller;
};

function cleanString(str) {
  return str.replace(/(\r\n|\n|\r)/gm, '').replace(/ +(?= )/g, '').trim();
}
