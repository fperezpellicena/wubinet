package com.wubinet.model;

import java.math.BigDecimal;

public enum MeasureType {
	TEMPERATURE("ºC"),
	HUMIDITY("%"),
	CO2_PERCENTAGE("%"),
	ATMOSPHERIC_PRESSURE("mbar");

	private final String unit;

	MeasureType(String unit) {
		this.unit = unit;
	}

	public String format(BigDecimal value) {
		return value.toString() + " " + unit;
	}
}
