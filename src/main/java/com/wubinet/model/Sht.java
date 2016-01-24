package com.wubinet.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

import static com.wubinet.model.MeasureType.HUMIDITY;
import static com.wubinet.model.MeasureType.TEMPERATURE;

public class Sht implements Sensor {

	private static final BigDecimal TREF = BigDecimal.valueOf(25);

	// Humidity coefficients
	private static final BigDecimal C1 = BigDecimal.valueOf(-2.0468);
	private static final BigDecimal C2 = BigDecimal.valueOf(0.0367);
	private static final BigDecimal C3 = BigDecimal.valueOf(-1.5955E-6);
	private static final BigDecimal T1 = BigDecimal.valueOf(0.01);
	private static final BigDecimal T2 = BigDecimal.valueOf(0.00008);

	// Temperature coefficients
	private static final BigDecimal D1 = BigDecimal.valueOf(-39.6);
	private static final BigDecimal D2 = BigDecimal.valueOf(0.01);

	private final BigDecimal temperature;

	private final BigDecimal humidity;

	public Sht(int temperature, int humidity) {
		this.temperature = BigDecimal.valueOf(temperature);
		this.humidity = BigDecimal.valueOf(humidity);
	}

	@Override
	public Map<MeasureType, BigDecimal> calculate() {
		Map<MeasureType, BigDecimal> values = new HashMap<>();
		values.put(TEMPERATURE, calculateTemperature());
		values.put(HUMIDITY, calculateHumidity());
		return values;
	}

	private BigDecimal calculateTemperature() {
		return D1.add(D2.multiply(temperature));
	}

	private BigDecimal calculateHumidity() {
		BigDecimal temperature = calculateTemperature();
		BigDecimal linearHumidity = calculateLinearHumidity();
		BigDecimal normalizedTemperature = temperature.subtract(TREF);
		BigDecimal compensatedHumidity = T1.add(T2.multiply(humidity));
		BigDecimal humidityValue = ((normalizedTemperature.multiply(compensatedHumidity).add(linearHumidity)));
		return humidityValue.setScale(2, RoundingMode.HALF_UP);
	}

	private BigDecimal calculateLinearHumidity() {
		BigDecimal humiditySqrd = humidity.pow(2);
		return C1.add(C2.multiply(humidity)).add(C3.multiply(humiditySqrd));
	}

	@Override
	public SensorType getType() {
		return SensorType.SHT11;
	}
}
