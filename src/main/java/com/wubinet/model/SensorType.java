package com.wubinet.model;

import lombok.Getter;

@Getter
public enum SensorType {

	SHT11('S', 4),
	IRCA1('I', 6),
	CO2D1('C', 2),
	BMP085('B', 4);

	private final char id;
	private final int dataLength;

	SensorType(char id, int dataLength) {
		this.id = id;
		this.dataLength = dataLength;
	}

	public static SensorType parse(int id) {
		switch (id) {
			case 'S':
				return SHT11;
			case 'I':
				return IRCA1;
			case 'C':
				return CO2D1;
			case 'B':
				return BMP085;
			default:
				throw new EnumConstantNotPresentException(SensorType.class, "Sensor id not found: " + id);
		}
	}
}
