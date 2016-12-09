'use strict';

const constants = require('./constants');

module.exports = {

  // urls para pesquisar vagas
  urlSearch(local, funcao, id) {
    if(!funcao)
      return constants.URL.SINE + '/vagas-empregos-em-' + local;

    if(!local)
      return constants.URL.SINE + '/vagas-empregos/' + funcao;

    if(!id)
      return constants.URL.SINE + '/vagas-empregos-em-' + local + '/' + funcao;

    return constants.URL.SINE + '/vagas-empregos-em-' + local + '/' + funcao + '/' + id;
  }
}