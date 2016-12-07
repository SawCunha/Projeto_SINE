#Enunciado:

Desenvolva uma aplicativo Android para acesso às informações do Site Nacional de Empregos (SINE), que atenda aos seguintes requisitos:

- Versão mínima: Android 4.4 (API 19)
- Interface nativa com Material Design (exceto a tela de gráficos, que deverá utilizar WebView e Chart.js)
- Persistência utilizando SQLite
- Desenvolvimento de uma API HTTP JSON intermediária em linguagem livre entre as consultas do dispositivo móvel e o site do SINE para economia de largura de banda

#Recursos do app:
Busca por vaga de emprego (por função/cidade/estado e com filtros "últimas vagas"/"maior salário"), que retorna uma listagem simplificada dos resultados da busca (vagas)
Ao clicar em um dos resultados, exibe as informações completas da vaga de emprego
Opção para compartilhar URL da vaga (via Intent do Android)
Possibilidade do usuário marcar uma vaga de emprego como "favorita", de modo que a mesma fique persistida no dispositivo (SQLite)
Opção de listar vagas marcadas como favoritas (não depende de internet)
Opção de excluir uma vaga da lista de favoritas (não depende de internet)
Busca de média salarial: o usuário informa um cargo e o tamanho da empresa (pequena, média ou grande) e é exibido um gráfico (Chart.js) com a média salarial para os níveis (Trainee, Júnior, Pleno, Sênior e Master)
