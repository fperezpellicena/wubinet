/** Plot variables */
var margin = {top: 20, right: 80, bottom: 30, left: 50};
var width = 800 - margin.left - margin.right;
var height = 500 - margin.top - margin.bottom;

var x = d3.time.scale().range([0, width]);
var yTemp = d3.scale.linear().range([height, 0]);
var yHum = d3.scale.linear().range([height, 0]);
var yCo2 = d3.scale.linear().range([height, 0]);

var color = d3.scale.category10();

var timestampAxis = d3.svg.axis().scale(x).orient("bottom");

var temperatureAxis = d3.svg.axis().scale(yTemp).orient("right");
var humidityAxis = d3.svg.axis().scale(yHum).orient("right");
var co2Axis = d3.svg.axis().scale(yCo2).orient("right");

var tempLine = d3.svg.line()
    .interpolate("basis")
    .x(function (d) {
        return x(d.date);
    })
    .y(function (d) {
        return yTemp(d.value);
    });

var humLine = d3.svg.line()
    .interpolate("basis")
    .x(function (d) {
        return x(d.date);
    })
    .y(function (d) {
        return yHum(d.value);
    });

var co2Line = d3.svg.line()
    .interpolate("basis")
    .x(function (d) {
        return x(d.date);
    })
    .y(function (d) {
        return yCo2(d.value);
    });

var svg;
var sensors;

/** Initialize plot */
function initPlot(address) {
    if (svg) {
        return;
    }
    loadData(address, createPlot);
    startPlotUpdater(address);
}

/** Update the plot data with the latest measures periodically */
function startPlotUpdater(address) {
    setInterval(function() {
        loadData(address, updatePlot);
    }, 2 * 1000);
}

/** Load measures */
function loadData(address, callback) {
    d3.json("http://localhost:8080/wubinet/" + address + "/measures",
        function(error, data){
            if (error) {
                throw error;
            }
            callback(data);
        });
}

/** */
function createPlot(data) {
    preparePathColors(data, color);
    prepareSensorData(data, color);
    prepareAbscissaAxis(x, data);
    prepareOrdinateAxis(sensors);
    createSvgPlot(width, height, margin);
    plotPaths(svg, sensors);
}

function updatePlot(data) {
    prepareSensorData(data, color);
    prepareAbscissaAxis(x, data);
    prepareOrdinateAxis(sensors);
    updatePaths(svg, sensors);
}