package com.wubinet.parser;

import com.wubinet.model.SensorType;

public class ParserFactory {

	public static Parser parserFor(SensorType sensorType, int[] data) {
		switch (sensorType) {
			case SHT11:
				return ShtParser.build(data);
			case IRCA1:
				return IrcaParser.build(data);
			default:
				throw new UnsupportedOperationException("Sensor parser not implemented for " + sensorType);
		}
	}
}
