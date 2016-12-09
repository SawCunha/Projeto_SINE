'use strict';
const Vaga = require('../models/vaga');
const request = require('request');
const cheerio = require('cheerio');

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
        vaga.salario = cleanString($('dd[itemprop="hiringOrganization"] ~ dd').contents().get(0).data);
        res.status(200).json(vaga);
      }
    });
  };

  return controller;
};

function cleanString(str) {
  return str.replace(/(\r\n|\n|\r)/gm, '').replace(/ +(?= )/g, '').trim();
}
