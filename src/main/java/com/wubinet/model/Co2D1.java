package com.wubinet.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Math.pow;

//
public class Co2D1 implements Sensor {

	private static final BigDecimal SLOPE = new BigDecimal(-10);				// -10mv/dec[CO2]
	private static final BigDecimal REFERENCE_VOLTAGE = new BigDecimal(2500);
	private static final BigDecimal PRECISION = new BigDecimal(1024);

	private final int voltage;

	public Co2D1(int voltage) {
		this.voltage = voltage;
	}

	@Override
	public Map<MeasureType, Object> calculate() {
		Map<MeasureType, Object> measures = new HashMap<>();
		measures.put(MeasureType.CO2_PERCENTAGE, calculateCO2());
		return measures;
	}

	private BigDecimal calculateCO2() {
		BigDecimal digitalVoltage = new BigDecimal(voltage);
		BigDecimal millivolts = digitalVoltage.multiply(REFERENCE_VOLTAGE.divide(PRECISION, 2, RoundingMode.HALF_UP));
		BigDecimal logarithmicConcentration = millivolts.divide(SLOPE, 2, RoundingMode.HALF_UP);
		double concentration = pow(10, logarithmicConcentration.doubleValue());
		return new BigDecimal(concentration).setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	@Override
	public SensorType getType() {
		return SensorType.CO2D1;
	}
}