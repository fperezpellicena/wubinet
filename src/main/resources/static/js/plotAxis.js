function prepareAbscissaAxis(x, data) {
    x.domain(d3.extent(data, function (d) {
        return d.timestamp;
    }));
}

function prepareOrdinateAxis(sensors) {
    prepareTemperatureAxis(sensors);
    prepareHumidityAxis(sensors)
    prepareCo2Axis(sensors);
}

function prepareTemperatureAxis(sensors) {
    yTemp.domain([
        d3.min(sensors, function (c) {
            if (c.name.indexOf('temperature') != -1) {
                return d3.min(c.values, function (v) {
                    return v.value;
                });
            } else {
                return 0;
            }
        }),
        d3.max(sensors, function (c) {
            if (c.name.indexOf('temperature') != -1) {
                return d3.max(c.values, function (v) {
                    return v.value;
                });
            } else {
                return 0;
            }
        })
    ]);
}

function prepareHumidityAxis(sensors) {
    yHum.domain([
        d3.min(sensors, function (c) {
            if (c.name.indexOf('humidity') != -1) {
                return d3.min(c.values, function (v) {
                    return v.value;
                });
            } else {
                return 0;
            }
        }),
        d3.max(sensors, function (c) {
            if (c.name.indexOf('humidity') != -1) {
                return d3.max(c.values, function (v) {
                    return v.value;
                });
            } else {
                return 0;
            }
        })
    ]);
}

function prepareCo2Axis(sensors) {
    yCo2.domain([
        d3.min(sensors, function (c) {
            if (c.name.indexOf('co2') != -1) {
                return d3.min(c.values, function (v) {
                    return v.value;
                });
            } else {
                return 0;
            }
        }),
        d3.max(sensors, function (c) {
            if (c.name.indexOf('co2') != -1) {
                return d3.max(c.values, function (v) {
                    return v.value;
                });
            } else {
                return 0;
            }
        })
    ]);
}
