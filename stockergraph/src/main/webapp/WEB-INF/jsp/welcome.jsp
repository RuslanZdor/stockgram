<html>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.7.2/Chart.bundle.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.7.2/Chart.js"></script>

	<body>
	<div>
	    <a href="reload">Reload</a>
	</div>
	<div style="height:675px; width:1800px;">
	    <canvas id="myChart" width="400" height="150"></canvas>
    </div>
	<div style="height:200px; width:1800px;">
    	<canvas id="oscillograph" width="400" height="50"></canvas>
    </div>

	<script>
		var ctx = document.getElementById("myChart").getContext('2d');
		var myChart = new Chart(ctx, {
			type: 'bar',
			data: {
                labels: ${labels},
                datasets: [
                    ${volume},
                    ${price},
                  	${minPrice},
                  	${maxPrice},
                  	${fiveSMA},
                  	${tenSMA},
                  	${fifteenSMA},
                  	${twentySMA},
                  	${twentyFiveSMA},
                  	${thirtySMA},
                  	${fiveEMA},
                  	${tenEMA},
                  	${fifteenEMA},
                  	${twentyEMA},
                  	${twentyFiveEMA},
                  	${thirtyEMA},
                  	${resistance},
                  	${support}
                ]
            },

			options: {
			    legend: {
			        display: true,
			        position: 'left'
			    },
                legendCallback: function(chart) {
                    var ul = document.createElement('ul');
                    chart.data.labels.forEach(function(label, index) {
                        ul.innerHTML += '<li><span></span>${label}</li>';
                    })
                    return ul.outerHTML;
                },
                title : {
                    display : true,
                    text : ${title}
                },
				scales: {
					responsive: true,
   					hoverMode: 'index',
   					stacked: false,
   					xAxes: [{
                    	display: false
                    }],
					yAxes: [{
						type: 'linear', // only linear but allow scale type registration. This allows extensions to exist solely for log scale for instance
						position: 'right',
						id: 'price',
					}, {
						type: 'linear', // only linear but allow scale type registration. This allows extensions to exist solely for log scale for instance
						display: false,
						position: 'right',
						id: 'volume',
						ticks: {
                            min: 0,
                            max: ${volumeMax}
                        },
						gridLines: {
							drawOnChartArea: true
						},
					}],
				}
			}
		});

		document.getElementById('js-legend-1').innerHTML = myChart.generateLegend();
		</script>

		<script>
        		var ctx = document.getElementById("oscillograph").getContext('2d');
        		var myChart = new Chart(ctx, {
        			type: 'bar',
        			data: {
                        labels: ${labels},
                        datasets: [
                            ${MACDLine},
                            ${MACDSignal}
                        ]
                    },

        			options: {
                        legend: {
                            display: true,
                            position: 'left'
                        },
                        legendCallback: function(chart) {
                            var ul = document.createElement('ul');
                            chart.data.labels.forEach(function(label, index) {
                                ul.innerHTML += '<li><span></span>${label}</li>';
                            })
                            return ul.outerHTML;
                        },
        				scales: {
        					responsive: true,
           					hoverMode: 'index',
           					stacked: false,
        					yAxes: [{
                                type: 'linear', // only linear but allow scale type registration. This allows extensions to exist solely for log scale for instance
                               	position: 'right',
                               	id: 'oscillator',
                               	gridLines: {
                               	    drawOnChartArea: true, // only want the grid lines for one axis to show up
                               	},
                            }],
        				}
        			}
        		});

        		document.getElementById('js-legend-2').innerHTML = myChart.generateLegend();
        		</script>
	</body>
</html>
