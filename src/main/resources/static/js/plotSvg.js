function createSvgPlot(width, height, margin) {
    svg = d3.select("div.main").append("svg")
        .attr("width", width + margin.left + margin.right)
        .attr("height", height + margin.top + margin.bottom)
        .append("g")
        .attr("transform", "translate(" + margin.left + "," + margin.top + ")");

    svg.append("g")
        .attr("class", "x axis")
        .attr("transform", "translate(0," + height + ")")
        .call(timestampAxis);

    svg.append("g")
        .attr("class", "y temp axis")
        .call(temperatureAxis)
        .append("text")
        .attr("transform", "rotate(-90)")
        .attr("y", -20)
        .attr("dy", ".71em")
        .style("text-anchor", "end")
        .text("Temperature (ºC)");

    svg.append("g")
        .attr("class", "y hum axis")
        .attr("transform", "translate(" + width + " ,0)")
        .call(humidityAxis)
        .append("text")
        .attr("transform", "rotate(-90)")
        .attr("y", 6)
        .attr("dy", ".71em")
        .style("text-anchor", "end")
        .text("Humidity (%)");

    svg.append("g")
        .attr("class", "y co2 axis")
        .attr("transform", "translate(" + width + " ,0)")
        .call(co2Axis)
        .append("text")
        .attr("transform", "rotate(-90)")
        .attr("y", 20)
        .attr("dy", ".71em")
        .style("text-anchor", "end")
        .text("Co2 concentration (%)");
}