function updatePaths(svg, sensors) {
    svg.select("g.x.axis").call(timestampAxis);
    svg.selectAll(".sensor").remove();
    plotPaths(svg, sensors);
}

function plotPaths(svg, sensors) {
    var sensor = svg.selectAll(".sensor")
        .data(sensors)
        .enter().append("g")
        .attr("class", "sensor");

    sensor.append("path")
        .attr("name", "temp")
        .attr("class", "line")
        .attr("d", function (d) {
            if (d.name.indexOf('temperature') != -1) {
                return tempLine(d.values);
            }
        })
        .style("stroke", function (d) {
            return color(d.name);
        });


    sensor.append("path")
        .attr("name", "hum")
        .attr("class", "line")
        .attr("d", function (d) {
            if (d.name.indexOf('humidity') != -1) {
                return humLine(d.values);
            }
        })
        .style("stroke", function (d) {
            return color(d.name);
        });

    sensor.append("path")
        .attr("name", "co2")
        .attr("class", "line")
        .attr("d", function (d) {
            if (d.name.indexOf('co2') != -1) {
                return co2Line(d.values);
            }
        })
        .style("stroke", function (d) {
            return color(d.name);
        });

    sensor.append("text")
        .datum(function (d) {
            return {name: d.name, value: d.values[d.values.length - 1]};
        })
        .attr("transform", function (d) {
            if (d.name.indexOf('humidity') != -1) {
                return "translate(" + x(d.value.date) + "," + yHum(d.value.value) + ")";
            } else if (d.name.indexOf('co2') != -1) {
                return "translate(" + x(d.value.date) + "," + yCo2(d.value.value) + ")";
            } else {
                return "translate(" + x(d.value.date) + "," + yTemp(d.value.value) + ")";
            }
        })
        .attr("x", 3)
        .attr("dy", ".35em")
        .text(function (d) {
            return d.name;
        });
}