
var ctx = document.getElementById("chart");
var salarios = [];
var tipoEmpresa
var funcao = ""
function montarSalarios(obj){
    funcao = obj.nome_funcao;
    for(k in obj['salarios'][tipoEmpresa])
        salarios.push(obj['salarios'][tipoEmpresa][k]);
	plotarGrafico();
}

function init(id,empresa){
console.log('INIT');
console.log(id);
console.log(empresa);
    tipoEmpresa = empresa;
    var xmlhttp = new XMLHttpRequest();
    xmlhttp.open('GET', 'http://162.243.119.96:10555/media-salarial?idfuncao='+id, true);
    xmlhttp.onreadystatechange = function() {
        if (xmlhttp.readyState == 4) {
            if(xmlhttp.status == 200) {
                var obj = JSON.parse(xmlhttp.responseText);
                            montarSalarios(obj.media_salarial);
             }
        }
    };
    xmlhttp.send(null);
}

function plotarGrafico(){

    document.querySelector("h4").innerHTML = funcao

	var data = {
	  labels: ["Trainee", "Junior", "Pleno", "Senior","Master"],
	  datasets: [{
	    label: "Salario",
	    backgroundColor: "blue",
			//dados de salario do Trainee nas tres empresas
	    data: salarios
	  }]
	};

	var myBarChart = new Chart(ctx, {
	  type: 'bar',
	  data: data,
	  options: {
	    barValueSpacing: 20,
	    animation:false,
	    scales: {
	      yAxes: [{
	        ticks: {
	          min: 0,
	        }
	      }]
	    }
	  }
	});
}
