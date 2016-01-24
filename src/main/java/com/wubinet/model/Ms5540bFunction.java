package com.wubinet.model;

import java.math.BigDecimal;

import static java.math.BigDecimal.TEN;
import static java.math.BigDecimal.valueOf;
import static java.math.RoundingMode.HALF_UP;

public class Ms5540bFunction {

	private static final BigDecimal SENSOR_RESOLUTION = BigDecimal.TEN;        // 0.1ºC

	private final BigDecimal c1;
	private final BigDecimal c2;
	private final BigDecimal c3;
	private final BigDecimal c4;
	private final BigDecimal c5;
	private final BigDecimal c6;

	public Ms5540bFunction(int[] coefficients) {
		this.c1 = valueOf(coefficients[0]);
		this.c2 = valueOf(coefficients[1]);
		this.c3 = valueOf(coefficients[2]);
		this.c4 = valueOf(coefficients[3]);
		this.c5 = valueOf(coefficients[4]);
		this.c6 = valueOf(coefficients[5]);
	}

	public BigDecimal calculateTemperature(BigDecimal temperature) {
		BigDecimal deltaTemp = calculateDeltaTemp(temperature);
		BigDecimal integerTemperature = valueOf(200).add(deltaTemp.multiply(c6.add(valueOf(50))).divide(valueOf(1024), 2, HALF_UP));
		return integerTemperature.divide(SENSOR_RESOLUTION, 1, HALF_UP);
	}

	private BigDecimal calculateDeltaTemp(BigDecimal temperature) {
		BigDecimal ut1 = c5.multiply(valueOf(8)).add(valueOf(20224));
		BigDecimal deltaTemp = temperature.subtract(ut1);
		return deltaTemp;
	}

	public BigDecimal calculatePressure(BigDecimal pressure, BigDecimal temperature) {
		BigDecimal deltaTemp = calculateDeltaTemp(temperature);
		BigDecimal off = calculateOffTerm(deltaTemp);
		BigDecimal sens = c1.add(c3.multiply(deltaTemp).divide(valueOf(1024), 0, HALF_UP)).add(valueOf(24576));
		BigDecimal x = sens.multiply(pressure.subtract(valueOf(7168))).divide(valueOf(16384), 0, HALF_UP).subtract(off);
		BigDecimal integerPressure = x.multiply(TEN).divide(valueOf(32), 0, HALF_UP).add(valueOf(2500));
		return integerPressure.divide(SENSOR_RESOLUTION, 1, HALF_UP);
	}

	private BigDecimal calculateOffTerm(BigDecimal deltaTemp) {
		BigDecimal offT1 = c2.multiply(valueOf(4));
		BigDecimal tco = c4.subtract(valueOf(512)).multiply(deltaTemp);
		BigDecimal secondTerm = tco.divide(valueOf(4096), 0, HALF_UP);
		return offT1.add(secondTerm);
	}
}
