package com.wubinet.model;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static com.wubinet.model.MeasureType.HUMIDITY;
import static com.wubinet.model.MeasureType.TEMPERATURE;

public class IntegritySensor implements Sensor {

	private Ntc ntc;
	private H25K5A h25K5A;

	public IntegritySensor(int temperature, int humidity) {
		this.ntc = new Ntc(temperature);
		this.h25K5A = new H25K5A(humidity);
	}

	@Override
	public Map<MeasureType, BigDecimal> calculate() {
		Map<MeasureType, BigDecimal> values = new HashMap<>();
		values.put(TEMPERATURE, calculateTemperature());
		values.put(HUMIDITY, calculateHumidity());
		return values;
	}

	private BigDecimal calculateTemperature() {
		return ntc.calculateTemperature();
	}

	private BigDecimal calculateHumidity() {
		BigDecimal temperature = ntc.calculateTemperature();
		BigDecimal humidity = h25K5A.calculateHumidity(temperature);
		return humidity;
	}

	@Override
	public SensorType getType() {
		return SensorType.INTEGRITY_SENSOR;
	}
}
