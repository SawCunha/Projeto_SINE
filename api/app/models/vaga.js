'use strict';
const constants = require('../resources/constants');

class Vaga {
  constructor(obj) {
    this.id = obj.id;
    this.titulo = obj.titulo;
    this.descricao = obj.descricao;
    this.endereco = obj.endereco;
    this.cidade = obj.cidade;
    this.funcao = obj.funcao;
    this.salario = obj.salario;
    this.empresa = obj.empresa;
    this.url_sine = this.createUrl(obj.cidade, obj.funcao, obj.id);
  }

  createUrl(cidade, funcao, vaga) {
    return constants.URL.SINE + '/vagas-empregos-em-' + cidade + '/' + funcao + '/' + vaga;
  }

  //Outros atributos
}

module.exports = Vaga;
