package com.wubinet.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static java.lang.Math.log;
import static java.math.BigDecimal.valueOf;

public class Ntc {

	private static final BigDecimal KELVIN_CONVERSION = valueOf(273);
	private static final BigDecimal ADC_RANGE = valueOf(1023);

	private static final BigDecimal B_NTC = valueOf(4300);
	private static final BigDecimal TEMPERATURE_REF = valueOf(298);
	private static final BigDecimal RESISTANCE_REF = valueOf(4700);
	private static final BigDecimal PULL_DOWN_RESISTENCE = valueOf(6200);

	private final BigDecimal temperature;

	public Ntc(int temperature) {
		this.temperature = valueOf(temperature);
	}

	public BigDecimal calculateTemperature() {
		BigDecimal resistanceRatio = PULL_DOWN_RESISTENCE.divide(RESISTANCE_REF, 2, RoundingMode.HALF_UP);
		BigDecimal voltageRatio = ADC_RANGE.divide(temperature, 2, RoundingMode.HALF_UP).subtract(BigDecimal.ONE);
		BigDecimal temperatureRatio = B_NTC.divide(TEMPERATURE_REF, 2, RoundingMode.HALF_UP);
		BigDecimal kelvinTemperarure = valueOf(log(voltageRatio.multiply(resistanceRatio).doubleValue())).add(temperatureRatio);
		kelvinTemperarure = B_NTC.divide(kelvinTemperarure, 2, RoundingMode.HALF_UP);
		BigDecimal normalizedTemperature = kelvinTemperarure.subtract(KELVIN_CONVERSION);
		return normalizedTemperature;
	}
}
