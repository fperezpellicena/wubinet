package com.wubinet.parser;

import com.rapplogic.xbee.util.ByteUtils;
import com.wubinet.model.IntegritySensor;
import com.wubinet.model.MeasureType;

import java.util.Map;

public class IntegritySensorParser implements Parser {

	private final int[] data;

	public IntegritySensorParser(int[] data) {
		this.data = data;
	}

	public static IntegritySensorParser build(int[] data) {
		return new IntegritySensorParser(data);
	}

	@Override
	public Map<MeasureType, Object> parse() {
		int[] temperatureBytes = {data[0], data[1]};
		int temperature = ByteUtils.convertMultiByteToInt(temperatureBytes);
		int[] humidityBytes = {data[2], data[3]};
		int humidity = ByteUtils.convertMultiByteToInt(humidityBytes);
		IntegritySensor integritySensor = new IntegritySensor(temperature, humidity);
		return integritySensor.calculate();
	}
}
