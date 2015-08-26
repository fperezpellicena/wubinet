package com.wubinet.parser;

import com.wubinet.model.SensorType;

public class ParserFactory {

	public static Parser parserFor(SensorType sensorType, int[] data) {
		switch (sensorType) {
			case SHT11:
				return ShtParser.build(data);
			case IRCA1:
				return IrcaParser.build(data);
			case CO2D1:
				return Co2D1Parser.build(data);
			case INTEGRITY_SENSOR:
				return IntegritySensorParser.build(data);
			case MS5540B:
				return Ms5540bParser.build(data);
			default:
				throw new UnsupportedOperationException("Sensor parser not implemented for " + sensorType);
		}
	}
}
