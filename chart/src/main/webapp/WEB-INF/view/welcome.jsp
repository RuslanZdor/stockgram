<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
        <script src="https://cdn.jsdelivr.net/npm/moment@2.24.0/min/moment.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/chart.js@2.8.0"></script>
        <script src="https://www.chartjs.org/chartjs-chart-financial/chartjs-chart-financial.js"></script>
    </head>


	<body>
	<div style="height:690px; width:1800px;">
	    <canvas id="myChart" width="400" height="150"></canvas>
    </div>
	<div style="height:190px; width:1800px;">
    	<canvas id="oscillograph" width="400" height="50"></canvas>
    </div>
	<div style="height:200px; width:1800px;">
    	<canvas id="rsi" width="400" height="50"></canvas>
    </div>
	<script>
		var ctx = document.getElementById("myChart").getContext('2d');
		var priceChart = new Chart(ctx, {
		    type: 'candlestick',
			data: {
                labels: ${labels},
                datasets: [
                    ${price},
                    ${volume},
                    ${fiveSMA}
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
						ticks: {
                            min: ${maxPrice},
                            max: ${minPrice}
                        }
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
				},
				animation: {
                    duration: 0
                }
			}
		});
		</script>

		<script>
        		var ctx = document.getElementById("oscillograph").getContext('2d');
        		var oscChart = new Chart(ctx, {
                    type: 'linear',
        			data: {
                        labels: ${labels},
                        datasets: [
                            ${MACDLine},
                            ${MACDSignal}
                        ]
                    },

        			options: {
                        legend: {
                            display: false
                        },
        				scales: {
        					responsive: true,
           					hoverMode: 'index',
           					stacked: false,
                            xAxes: [{
                                display: false
                            }],
        					yAxes: [{
                               	position: 'right',
                               	id: 'oscillator',
                               	gridLines: {
                               	    drawOnChartArea: true,
                               	},
                            }],
        				},
                         animation: {
                            duration: 0
                         }
        			}
        		});
        </script>

      	<script>
       		var ctx = document.getElementById("rsi").getContext('2d');
            var rsiChart = new Chart(ctx, {
                type: 'linear',
                data: {
                    labels: ${labels},
                    datasets: [
                        ${RSI30},
                        ${RSI5}
                    ]
                },

                options: {
                    legend: {
                        display: false
                    },
                	scales: {
                        responsive: true,
                        hoverMode: 'index',
                        stacked: false,
                        yAxes: [{
                            position: 'right',
                            id: 'oscillator',
                            gridLines: {
                                drawOnChartArea: true, // only want the grid lines for one axis to show up
                            },
                        }],
                        xAxes : [ {
                            display : true,
                        	type : "time",
                      		time: {
                                unit: 'day'
                            }
                        }]
                    },
                    animation: {
                       duration: 0
                    }
                }
            });
        </script>
	</body>
</html>
