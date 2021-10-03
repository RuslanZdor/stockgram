let options = {
    series: [],
    chart: {
        id: 'chart2',
        type: 'line',
        height: 350,
        background: "url(images/image.png)",
        toolbar: {
            autoSelected: 'pan',
            show: false
        }
    },
    colors:['#F44336', '#8aff58', '#fa3939'],
    stroke: {
        width: [2, 1, 1]
    },
    dataLabels: {
        enabled: false
    },
    fill: {
        type:'solid',
        opacity: [1, 0.5, 0.5],
    },
    markers: {
        size: 0
    },
    xaxis: {
        type: 'datetime'
    },
    yaxis: {
        min: 0.2,
        max: 0.8,
        tickAmount: 6,
        decimalsInFloat: 2
    },
    noData: {
        text: 'Loading...'
    }
};

let optionsLine = {
    series: [],
    chart: {
        id: 'chart1',
        height: 130,
        type: 'area',
        brush: {
            target: 'chart2',
            enabled: true
        },
        selection: {
            enabled: true,
            yaxis: {
                min: 0.2,
                max: 0.8,
                tickAmount: 6,
                decimalsInFloat: 2
            },
            xaxis: {
                type: 'datetime'
            }
        },
    },
    colors:['#F44336', '#8aff58', '#fa3939'],
    stroke: {
        width: [2, 1, 1]
    },
    fill: {
        type:'solid',
        opacity: [1, 0.5, 0.5],
    },
    xaxis: {
        type: 'datetime',
        tooltip: {
            enabled: false
        }
    },
    yaxis: {
        min: 0.2,
        max: 0.8,
        tickAmount: 6,
        decimalsInFloat: 2
    },
    noData: {
        text: 'Loading...'
    }
};

let chart = new ApexCharts(document.querySelector("#chart-line2 div"), options);

var chartLine = new ApexCharts(document.querySelector("#chart-line div"), optionsLine);

var url = 'https://vpa7u6ixdg.execute-api.us-west-2.amazonaws.com/market';

$(document).ready(function() {
    chart.render();
    chartLine.render();

    $.ajax({
        url: url,
        type: 'GET',
        dataType: 'json',
        success: function(responseString) {
            let chartData = []
            let lowBorder = []
            let highBorder = []
            responseString.forEach(day => {
                chartData.push([day.dateTimestamp, day.thrustTenSMA]);
                lowBorder.push([day.dateTimestamp, 0.4]);
                highBorder.push([day.dateTimestamp, 0.615]);
            });

            chart.updateSeries([{
                name: 'Ten Days Moving Average',
                type: 'line',
                data: chartData
            },{
                name: 'Oversold',
                type: 'line',
                data: lowBorder
            },{
                name: 'Overbought',
                type: 'line',
                data: highBorder
            }])

            chartLine.updateSeries([{
                name: 'Ten Days Moving Average',
                type: 'line',
                data: chartData
            },{
                name: 'Oversold',
                type: 'line',
                data: lowBorder
            },{
                name: 'Overbought',
                type: 'line',
                data: highBorder
            }])
        },
        error: function(e) { alert(e); },
    });
});
