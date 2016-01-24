package com.wubinet.model;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Co2D1 implements Sensor {

	private final Co2D1Function function;

	public Co2D1(int reference, int offsetMillivolts, int sense, int temperature) {
		this.function = new Co2D1Function(reference, offsetMillivolts, sense, temperature);
	}

	@Override
	public Map<MeasureType, BigDecimal> calculate() {
		Map<MeasureType, BigDecimal> measures = new HashMap<>();
		measures.put(MeasureType.CO2_PERCENTAGE, function.calculateCO2());
		return measures;
	}

	@Override
	public SensorType getType() {
		return SensorType.CO2D1;
	}
}