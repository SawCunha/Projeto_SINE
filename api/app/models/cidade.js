const constants = require('../resources/constants');
'use strict';

class Cidade {
  constructor(nome, estado) {
    this.label = this.createLabel(nome, estado);
    this.url = this.createURL(nome, estado);;
    this.nome = nome;
    this.estado = estado;
  }

  // Cria label e URL pelo nome e estado
  // Barbacena - MG
  createLabel(nome, estado) {
    return nome + ' - ' + estado;
  }

  // Cria URL pelo nome e estado
  // barbacena-mg
  createURL(nome, estado) {
    nome = this.accentFold(nome);
    return nome + '-' + estado;

    //formatar para url
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
