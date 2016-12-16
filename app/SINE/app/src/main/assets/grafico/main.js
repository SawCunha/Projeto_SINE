
var ctx = document.getElementById("chart");
var trainee = [];
var junior = [];
var pleno = [];
var senior = [];
var master = [];
var funcao = "";

function montarSalarios(obj){
console.log(obj);
    funcao = obj.nome_funcao;
	for(i in obj.salarios){
		trainee.push(obj.salarios[i]['trainee']);
		junior.push(obj.salarios[i]['junior']);
		pleno.push(obj.salarios[i]['pleno']);
		senior.push(obj.salarios[i]['senior']);
		master.push(obj.salarios[i]['master']);
	}
	plotarGrafico();
}

function init(id){
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
	  labels: ["Pequena", "Média", "Grande"],
	  datasets: [{
	    label: "Trainee",
	    backgroundColor: "blue",
			//dados de salario do Trainee nas tres empresas
	    data: trainee
	  }, {
	    label: "Júnior",
	    backgroundColor: "red",

			//dados de salario do JR nas tres empresas
	    data: junior
	  }, {
	    label: "Pleno",
	    backgroundColor: "green",

			//dados de salario do Trainee nas tres empresas
	    data: pleno
	  }, {
	    label: "Senior",
	    backgroundColor: "orange",

			//dados de salario do Trainee nas tres empresas
	    data: senior
	  }, {
	    label: "Master",
	    backgroundColor: "yellow",

			//dados de salario do Trainee nas tres empresas
	    data: master
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
