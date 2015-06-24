package com.wubinet.model;

import java.util.Map;

public interface Sensor {

	Map<MeasureType, Object> calculate();

	SensorType getType();
}
