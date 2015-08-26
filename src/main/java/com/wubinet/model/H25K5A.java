package com.wubinet.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static java.lang.Math.exp;
import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;
import static java.math.BigDecimal.valueOf;

public class H25K5A {

	private static final BigDecimal B_NTC = valueOf(3964);
	private static final BigDecimal KELVIN_CONVERSION = valueOf(273);
	private static final BigDecimal TEMPERATURE_REF = valueOf(298);
	private static final BigDecimal ADC_RANGE = valueOf(1023);
	private static final BigDecimal RESISTANCE_REF_KOHM = valueOf(50);
	private static final BigDecimal UPPER_LIMIT_TEMPERATURE = valueOf(50);

	private final BigDecimal humidity;

	public H25K5A(int humidity) {
		this.humidity = BigDecimal.valueOf(humidity);
	}

	public BigDecimal calculateHumidity(BigDecimal temperature) {
		if (temperatureOutOfRange(temperature)) {
			return BigDecimal.ZERO;
		}
		BigDecimal temperatureRatio = B_NTC.divide(TEMPERATURE_REF, 2, RoundingMode.HALF_UP);
		BigDecimal exponent = B_NTC.divide(temperature.add(KELVIN_CONVERSION), 2, RoundingMode.HALF_UP).subtract(temperatureRatio);
		BigDecimal ntcResistance = RESISTANCE_REF_KOHM.multiply(valueOf(exp(exponent.doubleValue())));
		BigDecimal resistance = ADC_RANGE.divide(humidity, 2, RoundingMode.HALF_UP).subtract(ONE).multiply(ntcResistance);
		return mappingHumidity(temperature, resistance);
	}

	// TODO
	private BigDecimal mappingHumidity(BigDecimal temperature, BigDecimal resistance) {
		return null;
	}

	private boolean temperatureOutOfRange(BigDecimal temperature) {
		return temperature.compareTo(ZERO) >= 0 && temperature.compareTo(UPPER_LIMIT_TEMPERATURE) <= 0;
	}
}
