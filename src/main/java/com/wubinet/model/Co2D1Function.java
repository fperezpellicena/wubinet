package com.wubinet.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Co2D1Function {

	private static final BigDecimal REFERENCE_VOLTAGE = new BigDecimal(2500);
	private static final BigDecimal PRECISION = new BigDecimal(1024);
	private static final BigDecimal TEMPERATURE_REF = new BigDecimal(293);

	// Temperature coefficients
	private static final BigDecimal D1 = BigDecimal.valueOf(-39.6);
	private static final BigDecimal D2 = BigDecimal.valueOf(0.01);
	private static final BigDecimal KELVIN_OFFSET = BigDecimal.valueOf(273);

	private static final BigDecimal A = new BigDecimal(-0.0002);
	private static final BigDecimal B = new BigDecimal(0.0013);
	private static final BigDecimal C = new BigDecimal(-0.0829);
	private static final BigDecimal D = new BigDecimal(4.53);

	private BigDecimal digitalVoltage;
	private final BigDecimal temperature;
	private final BigDecimal offset;

	public Co2D1Function(int reference, int offset, int sense, int temperature) {
		this.digitalVoltage = new BigDecimal(sense - reference);
		this.offset = new BigDecimal(offset);
		this.temperature = new BigDecimal(temperature);
	}

	public BigDecimal calculateCO2() {
		BigDecimal voltage = sensorVoltage().add(offset);
		BigDecimal temperatureCompensatedVoltage = compensateTemperature(voltage);
		double co2ppm = Math.pow(10, polynomialAdjust(temperatureCompensatedVoltage).doubleValue());
		return new BigDecimal(co2ppm).setScale(2, RoundingMode.HALF_UP);
	}

	private BigDecimal sensorVoltage() {
		return digitalVoltage.multiply(REFERENCE_VOLTAGE.divide(PRECISION, 2, RoundingMode.HALF_UP));
	}

	private BigDecimal polynomialAdjust(BigDecimal x) {
		BigDecimal xSquare = x.multiply(x);
		BigDecimal xCube = xSquare.multiply(x);
		return A.multiply(xCube).add(B.multiply(xSquare)).add(C.multiply(x)).add(D).setScale(2, RoundingMode.HALF_UP);
	}

	private BigDecimal compensateTemperature(BigDecimal millivolts) {
		return millivolts.subtract(temperatureCompensation());
	}

	private BigDecimal temperatureCompensation() {
		return (TEMPERATURE_REF.subtract(temperature())).multiply(BigDecimal.valueOf(0.5));
	}

	private BigDecimal temperature() {
		return D1.add(D2.multiply(temperature)).add(KELVIN_OFFSET);
	}
}
