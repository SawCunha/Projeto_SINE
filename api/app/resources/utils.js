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
  },

  urlGetMediaSalarial(idFuncao){
    return 'http://salariobr.com/api/Funcoes/RetornarInformacoesFuncao?funcao=#{idFuncao}'
            .replace('#{idFuncao}', idFuncao);
  },

  removerAcentos (str) {
    if (!str) { return ''; }
    var ret = '';
    for (var i = 0; i < str.length; i++) {
      ret += constants.ACCENT_MAP[str.charAt(i)] || str.charAt(i);
    }
    return ret;
  },
  
  formatarUrl(str) {
      str = this.removerAcentos(str);
      return str.replace(/_/g, '-')
          .replace(/ /g, '-')
          .replace(/:/g, '-')
          .replace(/\\/g, '-')
          .replace(/\//g, '-')
          .replace(/[^a-zA-Z0-9\-]+/g, '')
          .replace(/-{2,}/g, '-')
          .toLowerCase();
  },

  //Substitui as keys pelo estilo underscore
  keysToUnderscoreCase(obj){
    let arrayKeys = Object.keys(obj);

    arrayKeys.forEach((elem, i) => {
        let oldKey = elem;
        let newKey = elem.replace(/(.)([A-Z])/g, "$1_$2").toLowerCase(); //Substitui por underscore. Ex ('ABCde' => 'A_b_cde')

        //verifica se as keys são diferentes
        if (oldKey !== newKey || obj[oldKey] === Object(obj[oldKey])) {
            //faz a substituição das keys
            Object.defineProperty(obj, newKey, Object.getOwnPropertyDescriptor(obj, oldKey));

            if(oldKey !== newKey)
              delete obj[oldKey];

            //verifica se a key refere-se a um objeto, se sim, utiliza recursão
            if(obj[newKey] === Object(obj[newKey]))
                return this.keysToUnderscoreCase(obj[newKey]);
        }
    });
  }
}
