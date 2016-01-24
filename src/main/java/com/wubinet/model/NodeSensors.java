package com.wubinet.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@Getter @Setter
public class NodeSensors {

	private SensorType sensorType;

	private Collection<MeasureType> measureTypes;
}
