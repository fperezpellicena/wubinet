package com.wubinet.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Getter @Setter @ToString
public class Measure {

	private SensorType sensor;

	private Map<MeasureType, BigDecimal> values = new HashMap<>();

	public Object getMeasureTypeValue(MeasureType measureType) {
		if (values != null) {
			return values.get(measureType);
		} else {
			return null;
		}
	}
}
