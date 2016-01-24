function loadStatsData(address, sensorType, measureType, callback) {
    $.getJSON("http://localhost:8080/wubinet/" + address + "/stats?" +
            "sensorType=" + sensorType + "&measureType=" + measureType,
        function(data){
            callback(data);
    });
}

function initStatTableUpdater(address, rowId, sensorType, measureType, sleepPeriod) {
    var statsUpdater = null;
    clearInterval(statsUpdater);
    statsUpdater = setInterval(function() {
        loadStatsData(address, sensorType, measureType, function(data) {
            updateStatsTable(data, rowId);
        });
    }, sleepPeriod);
}

function initStats(address, rowId, sensorType, measureType, sleepPeriod) {
    loadStatsData(address, sensorType, measureType, function(data) {
        buildStatsTable(data, rowId, sensorType, measureType, address, sleepPeriod);
    });
}

function buildStatsTable(data, rowId, sensorType, measureType, address, sleepPeriod) {
    var $row = $(rowId);
    if ($row.length < 1) {
        return;
    }
    addRowData($row, data);
    if (sleepPeriod) {
        initStatTableUpdater(address, rowId, sensorType, measureType, sleepPeriod);
    }
}

function updateStatsTable(data, rowId) {
    var $row = $(rowId);
    $row.empty();
    addRowData($row, data);
}

function addRowData($row, data) {
    var sensor = data.sensor;
    var max = data.max;
    var min = data.min;
    var avg = data.avg;

    var $sensorData = $('<td></td>');
    $sensorData.text(sensor);
    var $maxData = $('<td></td>');
    $maxData.text(max);
    var $minData = $('<td></td>');
    $minData.text(min);
    var $avgData = $('<td></td>');
    $avgData.text(avg);

    $row.append($sensorData);
    $row.append($maxData);
    $row.append($minData);
    $row.append($avgData);
}