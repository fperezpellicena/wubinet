package com.wubinet.model;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static com.wubinet.model.MeasureType.ATMOSPHERIC_PRESSURE;
import static com.wubinet.model.MeasureType.TEMPERATURE;
import static java.math.BigDecimal.valueOf;

public class Ms5540b implements Sensor {

	private final Ms5540bFunction function;
	private final BigDecimal temperature;
	private final BigDecimal pressure;

	public Ms5540b(int[] coefficients, int pressure, int temperature) {
		this.function = new Ms5540bFunction(coefficients);
		this.temperature = valueOf(temperature);
		this.pressure= valueOf(pressure);

	}

	@Override
	public Map<MeasureType, BigDecimal> calculate() {
		Map<MeasureType, BigDecimal> values = new HashMap<>();
		values.put(TEMPERATURE, function.calculateTemperature(temperature));
		values.put(ATMOSPHERIC_PRESSURE, function.calculatePressure(pressure, temperature));
		return values;
	}

	@Override
	public SensorType getType() {
		return SensorType.MS5540B;
	}
}
