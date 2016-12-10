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
  },

  // url que contem o id da função
  urlGetIdFuncao(funcao) {
    return 'http://salariobr.com/api/Funcoes/converter?funcao=' + funcao + '&origem=Sine';
  },

  // url que contem o id da cidade
  urlGetIdCidade(cidade) {
    return 'http://www.sine.com.br/api/v1.0/Cidade/List?partialDesc=' + cidade;
  },

  // url que contem as informações da vaga
  urlGetInfoVagas(idFuncao, idCidade, numPagina, tipoOrdenacao){
    return 'http://www.sine.com.br/api/v1.0/Job/List?idFuncao=#{idFuncao}&idCidade=#{idCidade}&pagina=#{numPagina}&pesquisa=&ordenacao=#{tipoOrdenacao}&idUsuario=NaN'
            .replace('#{idFuncao}', idFuncao)
            .replace('#{idCidade}', idCidade)
            .replace('#{numPagina}', numPagina)
            .replace('#{tipoOrdenacao}', tipoOrdenacao);
  }
}