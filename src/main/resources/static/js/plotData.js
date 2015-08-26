function preparePathColors(data, color) {
    color.domain(d3.keys(data[0]).filter(function (key) {
        return key !== "timestamp";
    }));
}

function prepareSensorData(data, color) {
    data.forEach(function(d) {
        d.timestamp = d3.time.format("%H:%M:%S").parse(d.timestamp);
    });

    sensors = color.domain().map(function(name) {
        return {
            name: name,
            values: data.map(function(d) {
                return {date: d.timestamp, value: +d[name]};
            })
        };
    });
}

