package com.wubinet.service;

import com.wubinet.model.MeasureType;
import com.wubinet.model.SensorType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter
public class NodeStats {

	private SensorType sensor;
	private MeasureType measureType;
	private BigDecimal avg;
	private BigDecimal max;
	private BigDecimal min;

	public String getSensorName() {
		String sensorName = sensor.name();
		sensorName = sensorName.replace("_", " ");
		return sensorName.toLowerCase() + " " + measureType.name().toLowerCase();
	}

	public String getMaxValue() {
		return measureType.format(max);
	}

	public String getMinValue() {
		return measureType.format(min);
	}

	public String getAvgValue() {
		return measureType.format(avg);
	}

	public String toJson() {
		return "{" +
			"\"sensor\":\"" + getSensorName() + "\"," +
			"\"max\":\"" + getMaxValue() + "\"," +
			"\"min\":\"" + getMinValue() + "\"," +
			"\"avg\":\"" + getAvgValue() + "\"" +
		"}";
	}
}
