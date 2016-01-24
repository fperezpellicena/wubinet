package com.wubinet.model;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public enum SensorType {

	SHT11('S', 4, MeasureType.TEMPERATURE, MeasureType.HUMIDITY),
	IRCA1('I', 6, MeasureType.CO2_PERCENTAGE),
	CO2D1('C', 8, MeasureType.CO2_PERCENTAGE),
	INTEGRITY_SENSOR('T', 4, MeasureType.TEMPERATURE, MeasureType.HUMIDITY),
	MS5540B('M', 16, MeasureType.ATMOSPHERIC_PRESSURE, MeasureType.TEMPERATURE);

	private final char id;
	private final int dataLength;
	private final List<MeasureType> measureTypes;


	SensorType(char id, int dataLength, MeasureType... measureTypes) {
		this.id = id;
		this.dataLength = dataLength;
		this.measureTypes = Arrays.asList(measureTypes);
	}

	public static NodeSensors nodeSensors(SensorType sensorType) {
		NodeSensors nodeSensors = new NodeSensors();
		nodeSensors.setSensorType(sensorType);
		nodeSensors.setMeasureTypes(sensorType.getMeasureTypes());
		return nodeSensors;
	}

	public static SensorType parse(int id) {
		switch (id) {
			case 'S':
				return SHT11;
			case 'I':
				return IRCA1;
			case 'C':
				return CO2D1;
			case 'M':
				return MS5540B;
			case 'T':
				return INTEGRITY_SENSOR;
			default:
				throw new EnumConstantNotPresentException(SensorType.class, "Sensor id not found: " + id);
		}
	}
}
