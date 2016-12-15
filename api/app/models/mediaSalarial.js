'use strict';

class MediaSalarial {
  constructor() {
    this.nomeFuncao;
    this.descricaoFuncao;
    this.salarios = {
      'pequenaEmpresa': {
        'trainee': undefined,
        'junior': undefined,
        'pleno': undefined,
        'senior': undefined,
        'master': undefined,
      },
      'mediaEmpresa': {
        'trainee': undefined,
        'junior': undefined,
        'pleno': undefined,
        'senior': undefined,
        'master': undefined,
      },
      'grandeEmpresa': {
        'trainee': undefined,
        'junior': undefined,
        'pleno': undefined,
        'senior': undefined,
        'master': undefined,
      }
    }
  }
}

module.exports = MediaSalarial;