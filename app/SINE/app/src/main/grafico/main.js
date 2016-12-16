
	var ctx = document.getElementById("chart");


	var data = {
	  labels: ["Pequena", "Média", "Grande"],
	  datasets: [{
	    label: "Trainee",
	    backgroundColor: "blue",
			//dados de salario do Trainee nas tres empresas
	    data: [500, 650, 675]
	  }, {
	    label: "Júnior",
	    backgroundColor: "red",

			//dados de salario do JR nas tres empresas
	    data: [600, 750, 775]
	  }, {
	    label: "Pleno",
	    backgroundColor: "green",

			//dados de salario do Trainee nas tres empresas
	    data: [700, 850, 885]
	  }, {
	    label: "Senior",
	    backgroundColor: "orange",

			//dados de salario do Trainee nas tres empresas
	    data: [800, 950, 985]
	  }, {
	    label: "Master",
	    backgroundColor: "yellow",

			//dados de salario do Trainee nas tres empresas
	    data: [900, 1050, 1085]
	  }]
	};

	var myBarChart = new Chart(ctx, {
	  type: 'bar',
	  data: data,
	  options: {
	    barValueSpacing: 20,
	    scales: {
	      yAxes: [{
	        ticks: {
	          min: 0,
	        }
	      }]
	    }
	  }
	});
