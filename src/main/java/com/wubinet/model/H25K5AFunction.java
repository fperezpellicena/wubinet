package com.wubinet.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class H25K5AFunction {

	private static final int DEFAULT_MIN_HUMIDITY = 0;
	private static final int DEFAULT_MAX_HUMIDITY = 100;
	private static final int MIN_HUMIDITY = 20;
	private static final int MAX_HUMIDITY = 90;
	private static final int TEMPERATURE_INTERVALS = 11;
	private static final int HUMIDITY_INTERVALS = 15;

	private static final double[] table = new double[]{
			//  	0º                                                     50º
			0, 0, 0, 21000, 13500, 9800, 8000, 6300, 4600, 3800, 3200,                // 20%
			0, 19800, 16000, 10500, 6700, 4803, 3900, 3100, 2300, 1850, 1550,
			12000, 9800, 7200, 5100, 3300, 2500, 2000, 1500, 1100, 900, 750,
			5200, 4700, 3200, 2350, 1800, 1300, 980, 750, 575, 430, 350,
			2800, 2000, 1400, 1050, 840, 630, 470, 385, 282, 210, 170,
			720, 510, 386, 287, 216, 166, 131, 104, 80, 66, 51,
			384, 271, 211, 159, 123, 95, 77, 63, 52, 45, 38,
			200, 149, 118, 91, 70, 55, 44, 38, 32, 30, 24,
			108, 82, 64, 51, 40, 31, 25, 21, 17, 14, 12,
			64, 48, 38, 31, 25, 20, 17, 13, 11, 9, 8,
			38, 29, 24, 19, 16, 13, 10.5, 9, 8.2, 7.1, 6.0,
			23, 18, 15, 12, 10, 8.5, 7.2, 6.4, 5.8, 5.0, 4.1,
			16, 12, 10.2, 8.1, 7.2, 5.7, 5.0, 4.4, 4.0, 3.3, 2.9,
			10.2, 8.2, 6.9, 5.5, 4.7, 4.0, 3.6, 3.2, 2.9, 2.4, 2.0,
			6.9, 5.4, 4.7, 4.1, 3.2, 2.8, 2.5, 2.3, 2.1, 1.8, 1.5                        // 90%
	};

	public int getRelativeHumidity(int temperature, double resistance) {
		if (temperature < 0) {
			return DEFAULT_MIN_HUMIDITY;
		} else if (temperature > 50) {
			return DEFAULT_MAX_HUMIDITY;
		} else {
			List<Double> availableResistances = getAvailableResistances(temperature);
			double mappedResistance = getMappedResistance(resistance, availableResistances);
			return calculateHumidity(availableResistances, mappedResistance);
		}

	}

	private int calculateHumidity(List<Double> availableResistances, double mappedResistance) {
		int humidityRowIndex = availableResistances.indexOf(mappedResistance);
		return MIN_HUMIDITY + Math.round(humidityRowIndex * getSlope());
	}

	private int getSlope() {
		BigDecimal numerator = new BigDecimal(MAX_HUMIDITY - MIN_HUMIDITY);
		BigDecimal denominator = new BigDecimal(HUMIDITY_INTERVALS);
		BigDecimal slope = numerator.divide(denominator, 2, RoundingMode.HALF_UP);
		BigDecimal roundedSlope = slope.setScale(0, RoundingMode.HALF_UP);
		return roundedSlope.intValue();
	}

	private List<Double> getAvailableResistances(int temperature) {
		List<Double> availableResistances = new ArrayList<>();
		int indexTempBase = Math.round(temperature * 0.2f);
		for (int humidityInterval = 0; humidityInterval < HUMIDITY_INTERVALS; humidityInterval++) {
			availableResistances.add(table[indexTempBase + TEMPERATURE_INTERVALS * humidityInterval]);
		}
		return availableResistances;
	}

	private double getMappedResistance(double resistance, List<Double> availableResistances) {
		double mappedResistance = 0;
		double minDeltaResistance = Integer.MAX_VALUE;
		for (Double availableResistance : availableResistances) {
			double deltaResistance = deltaResistance(availableResistance, resistance);
			if (deltaResistance < minDeltaResistance) {
				minDeltaResistance = deltaResistance;
				mappedResistance = availableResistance;
			}
		}
		return mappedResistance;
	}

	private double deltaResistance(double r, double resistance) {
		return Math.abs(r - resistance);
	}


}
