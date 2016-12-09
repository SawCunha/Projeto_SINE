'use strict';

const constants = require('../resources/constants');

class Cidade {
  constructor(nome, estado) {
    this.label = this.createLabel(nome, estado);
    this.url = this.createURL(nome, estado);;
    this.nome = nome;
    this.estado = estado;
  }

  createLabel(nome, estado) {
    return nome + ' - ' + estado;
  }

  createURL(nome, estado) {
    nome = this.accentFold(nome) + '-' + estado;
    return nome.toLowerCase();
  }

  //Remove acentuação
  accentFold (s) {
    if (!s) { return ''; }

    let ret = '';
    for (let i = 0; i < s.length; i++) {
      ret += constants.ACCENT_MAP[s.charAt(i)] || s.charAt(i);
    };

    return ret;
  };
}

module.exports = Cidade;
