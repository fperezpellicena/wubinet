/** Load measures */
function loadChartData(address, callback) {
    $.getJSON("http://localhost:8080/wubinet/" + address + "/measures",
        function( data){
            callback(formattedData(data));
    });
}

function initChartUpdater(address, chart, sleepPeriod) {
    var chartUpdater = setInterval(function(){}, 1000);
    gatherMeasures(address, chart, chartUpdater, sleepPeriod);
    setInterval(function(){
        gatherSleepPeriod(function(currentSleepPeriod) {
            gatherMeasures(address, chart, chartUpdater, currentSleepPeriod);
        });
    }, 20000);
}

function gatherMeasures(address, chart, chartUpdater, sleepPeriod) {
    clearInterval(chartUpdater);
    chartUpdater = setInterval(function() {
        $.getJSON("http://localhost:8080/wubinet/" + address + "/measures",
            function(data) {
                chart.dataProvider = formattedData(data);
                chart.validateData();
            });
    }, sleepPeriod);
}

function gatherSleepPeriod(callback) {
    $.getJSON("http://localhost:8080/wubinet/sleepPeriod",
        function(data) {
            var sleepPeriod = data.sleep_period;
            callback(sleepPeriod);
    });
}

function formattedData(data) {
    $.each(data, function(index) {
        data[index].timestamp = AmCharts.stringToDate(data[index].timestamp, "DD-MM-YYYY HH:NN:SS");
    });
    return data;
}

function initCharts(address, integrity_chart, sensors_chart, sleepPeriod) {
    loadChartData(address, function(data) {
        buildIntegrityChart(data, integrity_chart, address, sleepPeriod);
        buildMs5540Co2Chart(data, sensors_chart, address, sleepPeriod);
    });
}

function buildIntegrityChart(chartData, container, address, sleepPeriod) {
    var chart;
    AmCharts.ready(function () {
        // SERIAL CHART
        chart = new AmCharts.AmSerialChart();

        chart.export = {
            "enabled": true,
            "libs": {
                "path": "http://amcharts.com/lib/3/plugins/export/libs/"
            }
        };
        chart.dataProvider = chartData;
        chart.categoryField = "timestamp";

        var categoryAxis = chart.categoryAxis;
        categoryAxis.parseDates = true;
        categoryAxis.minPeriod = "ss";

        // listen for "dataUpdated" event (fired when chart is inited) and call zoomChart method when it happens
        chart.addListener("dataUpdated", function() {
            zoomChart(chart);
        });

        // AXES
        // first value axis (on the left)
        var valueAxis1 = new AmCharts.ValueAxis();
        valueAxis1.axisColor = "#FF6600";
        valueAxis1.axisThickness = 2;
        valueAxis1.gridAlpha = 0;
        chart.addValueAxis(valueAxis1);

        // second value axis (on the right)
        var valueAxis2 = new AmCharts.ValueAxis();
        valueAxis2.position = "right"; // this line makes the axis to appear on the right
        valueAxis2.axisColor = "#FCD202";
        valueAxis2.gridAlpha = 0;
        valueAxis2.axisThickness = 2;
        chart.addValueAxis(valueAxis2);

        // GRAPHS
        // first graph
        var graph1 = new AmCharts.AmGraph();
        graph1.valueAxis = valueAxis1; // we have to indicate which value axis should be used
        graph1.title = "SHT11 Temperature";
        graph1.valueField = "sht11_temperature";
        graph1.bullet = "round";
        graph1.hideBulletsCount = 30;
        graph1.bulletBorderThickness = 1;
        chart.addGraph(graph1);

        // second graph
        var graph2 = new AmCharts.AmGraph();
        graph2.valueAxis = valueAxis2; // we have to indicate which value axis should be used
        graph2.title = "SHT11 Humidity %";
        graph2.valueField = "sht11_humidity";
        graph2.bullet = "square";
        graph2.hideBulletsCount = 30;
        graph2.bulletBorderThickness = 1;
        chart.addGraph(graph2);

        // third graph
        var graph3 = new AmCharts.AmGraph();
        graph3.valueAxis = valueAxis2; // we have to indicate which value axis should be used
        graph3.valueField = "integrity_sensor_humidity";
        graph3.title = "Integrity Sensor Humidity %";
        graph3.bullet = "triangleDown";
        graph3.hideBulletsCount = 30;
        graph3.bulletBorderThickness = 1;
        chart.addGraph(graph3);

        // forth graph
        var graph4 = new AmCharts.AmGraph();
        graph4.valueAxis = valueAxis1; // we have to indicate which value axis should be used
        graph4.valueField = "integrity_sensor_temperature";
        graph4.title = "Integrity Sensor Temperature";
        graph4.bullet = "diamond";
        graph4.hideBulletsCount = 30;
        graph4.bulletBorderThickness = 1;
        chart.addGraph(graph4);

        // CURSOR
        var chartCursor = new AmCharts.ChartCursor();
        chartCursor.cursorAlpha = 0.1;
        chartCursor.fullWidth = true;
        chart.addChartCursor(chartCursor);

        // SCROLLBAR
        var chartScrollbar = new AmCharts.ChartScrollbar();
        chartScrollbar.resizeEnabled = false;
        chart.addChartScrollbar(chartScrollbar);

        // LEGEND
        var legend = new AmCharts.AmLegend();
        legend.marginLeft = 110;
        legend.useGraphSettings = true;
        chart.addLegend(legend);

        // WRITE
        chart.write(container);

        if (sleepPeriod) {
            initChartUpdater(address, chart, sleepPeriod);
        }
    });
}

function buildMs5540Co2Chart(chartData, container, address, sleepPeriod) {
    var chart;
    AmCharts.ready(function () {
        // SERIAL CHART
        chart = new AmCharts.AmSerialChart();
        chart.export = {
            "enabled": true,
            "libs": {
                "path": "http://amcharts.com/lib/3/plugins/export/libs/"
            }
        };

        chart.dataProvider = chartData;
        chart.categoryField = "timestamp";

        var categoryAxis = chart.categoryAxis;
        categoryAxis.parseDates = true;
        categoryAxis.minPeriod = "ss";

        // listen for "dataUpdated" event (fired when chart is inited) and call zoomChart method when it happens
        chart.addListener("dataUpdated", function() {
            zoomChart(chart);
        });

        // AXES
        // first value axis (on the left)
        var valueAxis1 = new AmCharts.ValueAxis();
        valueAxis1.axisColor = "#FF6600";
        valueAxis1.axisThickness = 2;
        valueAxis1.gridAlpha = 0;
        chart.addValueAxis(valueAxis1);

        // second value axis (on the right)
        var valueAxis2 = new AmCharts.ValueAxis();
        valueAxis2.position = "left"; // this line makes the axis to appear on the right
        valueAxis2.axisColor = "#FCD202";
        valueAxis2.gridAlpha = 0;
        valueAxis2.axisThickness = 2;
        chart.addValueAxis(valueAxis2);

        // third value axis (on the right)
        var valueAxis3 = new AmCharts.ValueAxis();
        valueAxis3.position = "right"; // this line makes the axis to appear on the right
        valueAxis3.axisColor = "#B0DE09";
        valueAxis3.gridAlpha = 0;
        valueAxis3.axisThickness = 2;
        chart.addValueAxis(valueAxis3);

        // GRAPHS
        // first graph
        var graph1 = new AmCharts.AmGraph();
        graph1.valueAxis = valueAxis1; // we have to indicate which value axis should be used
        graph1.title = "Co2d1";
        graph1.valueField = "co2d1_co2_percentage";
        graph1.bullet = "round";
        graph1.hideBulletsCount = 30;
        graph1.bulletBorderThickness = 1;
        chart.addGraph(graph1);

        // second graph
        var graph2 = new AmCharts.AmGraph();
        graph2.valueAxis = valueAxis1; // we have to indicate which value axis should be used
        graph2.title = "IRCA1";
        graph2.valueField = "irca1_co2_percentage";
        graph2.bullet = "square";
        graph2.hideBulletsCount = 30;
        graph2.bulletBorderThickness = 1;
        chart.addGraph(graph2);

        // third graph
        var graph3 = new AmCharts.AmGraph();
        graph3.valueAxis = valueAxis2; // we have to indicate which value axis should be used
        graph3.valueField = "ms5540b_temperature";
        graph3.title = "Ms5540b temperature ºC";
        graph3.bullet = "triangleDown";
        graph3.hideBulletsCount = 30;
        graph3.bulletBorderThickness = 1;
        chart.addGraph(graph3);

        // forth graph
        var graph4 = new AmCharts.AmGraph();
        graph4.valueAxis = valueAxis3; // we have to indicate which value axis should be used
        graph4.valueField = "ms5540b_atmospheric_pressure";
        graph4.title = "Atmospheric pressure";
        graph4.bullet = "diamond";
        graph4.hideBulletsCount = 30;
        graph4.bulletBorderThickness = 1;
        chart.addGraph(graph4);

        // CURSOR
        var chartCursor = new AmCharts.ChartCursor();
        chartCursor.cursorAlpha = 0.1;
        chartCursor.fullWidth = true;
        chart.addChartCursor(chartCursor);

        // SCROLLBAR
        var chartScrollbar = new AmCharts.ChartScrollbar();
        chartScrollbar.resizeEnabled = false;
        chart.addChartScrollbar(chartScrollbar);

        // LEGEND
        var legend = new AmCharts.AmLegend();
        legend.marginLeft = 110;
        legend.useGraphSettings = true;
        chart.addLegend(legend);

        // WRITE
        chart.write(container);

        if (sleepPeriod) {
            initChartUpdater(address, chart, sleepPeriod);
        }
    });
}

// this method is called when chart is first inited as we listen for "dataUpdated" event
function zoomChart(chart) {
    // different zoom methods can be used - zoomToIndexes, zoomToDates, zoomToCategoryValues
    chart.zoomOut();
}