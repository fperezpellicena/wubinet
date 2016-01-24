package com.wubinet.model;

import java.math.BigDecimal;
import java.util.Map;

public interface Sensor {

	Map<MeasureType, BigDecimal> calculate();

	SensorType getType();
}
