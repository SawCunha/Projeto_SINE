'use strict';
const constants = require('../resources/constants');
const utils = require('../resources/utils');

class Vaga {
  constructor(obj) {
    this.id = obj.id;
    this.titulo = obj.titulo;
    this.telefone = obj.telefone;
    this.descricao = obj.descricao;
    this.endereco = obj.endereco;
    this.cidade = obj.cidade;
    this.funcao = obj.funcao;
    this.salario = obj.salario;
    this.empresa = obj.empresa;
    this.url_sine = this.createUrl();
  }

  createUrl(cidade, funcao, id) {
    return constants.URL.SINE + '/vagas-empregos-em-' + utils.formatarUrl(this.cidade) + '/' + utils.formatarUrl(this.funcao) + '/' + this.id
  }
}

module.exports = Vaga;
