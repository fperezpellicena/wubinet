package com.wubinet.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

import static com.wubinet.model.MeasureType.ATMOSPHERIC_PRESSURE;
import static com.wubinet.model.MeasureType.TEMPERATURE;
import static java.math.BigDecimal.TEN;
import static java.math.BigDecimal.valueOf;

public class Ms5540b implements Sensor {

	// Coefficients
	private final BigDecimal c1;
	private final BigDecimal c2;
	private final BigDecimal c3;
	private final BigDecimal c4;
	private final BigDecimal c5;
	private final BigDecimal c6;

	private final BigDecimal temperature;
	private final BigDecimal pressure;

	public Ms5540b(int[] coefficients, int pressure, int temperature) {
		this.c1 = valueOf(coefficients[0]);
		this.c2 = valueOf(coefficients[1]);
		this.c3 = valueOf(coefficients[2]);
		this.c4 = valueOf(coefficients[3]);
		this.c5 = valueOf(coefficients[4]);
		this.c6 = valueOf(coefficients[5]);
		this.temperature = valueOf(temperature);
		this.pressure= valueOf(pressure);
	}

	@Override
	public Map<MeasureType, Object> calculate() {
		Map<MeasureType, Object> values = new HashMap<>();
		values.put(TEMPERATURE, calculateTemperature());
		values.put(ATMOSPHERIC_PRESSURE, calculatePressure());
		return values;
	}

	private BigDecimal calculateTemperature() {
		BigDecimal deltaTemp = calculateDeltaTemp();
		return valueOf(200).add(deltaTemp.multiply(c6.add(valueOf(50))).divide(valueOf(1024), 2, RoundingMode.HALF_UP));
	}

	private BigDecimal calculateDeltaTemp() {
		BigDecimal ut1 = c5.multiply(valueOf(8)).add(valueOf(20224));
		BigDecimal deltaTemp = temperature.subtract(ut1);
		return deltaTemp;
	}

	private BigDecimal calculatePressure() {
		BigDecimal deltaTemp = calculateDeltaTemp();
		BigDecimal off = c2.multiply(valueOf(4)).add(c4.subtract(valueOf(512)).multiply(deltaTemp).divide(valueOf(8192), 2, RoundingMode.HALF_UP));
		BigDecimal sens = c1.add(c3.multiply(deltaTemp).divide(valueOf(1024), 2, RoundingMode.HALF_UP)).add(valueOf(24576));
		BigDecimal x = sens.multiply(pressure.subtract(valueOf(7168))).divide(valueOf(16384), 2, RoundingMode.HALF_UP).subtract(off);
		BigDecimal pressure = x.multiply(TEN).divide(valueOf(32), 2, RoundingMode.HALF_UP).add(valueOf(2500));
		return pressure;
	}

	@Override
	public SensorType getType() {
		return SensorType.MS5540B;
	}
}
