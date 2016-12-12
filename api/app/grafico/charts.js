'use strict';
var ctx = document.getElementById("myChart"),
	
function geraGraficoBarras(dados) {
	var myChart = new Chart(ctx, {
		type: 'bar',
		data: {
			labels: ["Trainee", "Junior", "Pleno", "Sênior", "Master"],
			datasets: [{
				label: 'Media (R$)',
				data: dados,
				backgroundColor: [
					'rgba(255, 99, 132, 0.5)',
					'rgba(54, 162, 235, 0.5)',
					'rgba(255, 206, 86, 0.5)',
					'rgba(51, 204, 51, 0.5)',
					'rgba(255, 102, 0, 0.5)'
				],
				borderColor: [
					'rgba(255,99,132,1)',
					'rgba(54, 162, 235, 1)',
					'rgba(255, 206, 86, 1)',
					'rgba(51, 204, 51, 1)',
					'rgba(255, 102, 0, 1)'
				],
				borderWidth: 1
			}]
		},
		options: {
			responsive: true,
			title: {
				display: true,
				text:  "Media Salarial por nível",
				fullWidth: false
			},
			scales: {
				yAxes: [{
					ticks: {
						beginAtZero: true
					}
				}]
			}
		}
	});
}

geraGraficoBarras(dado);
