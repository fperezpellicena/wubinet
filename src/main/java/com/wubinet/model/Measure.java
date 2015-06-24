package com.wubinet.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

@Getter @Setter @ToString
public class Measure {

	private SensorType sensor;
	private Map<MeasureType, Object> values;
}
