package com.wubinet.parser;

import com.rapplogic.xbee.util.ByteUtils;
import com.wubinet.model.MeasureType;
import com.wubinet.model.Sht;

import java.util.Map;

public class ShtParser implements Parser {

	private final int[] data;

	public ShtParser(int[] data) {
		this.data = data;
	}

	public static ShtParser build(int[] data) {
		return new ShtParser(data);
	}

	public Map<MeasureType, Object> parse() {
		int[] temperatureBytes = {data[0], data[1]};
		int temperature = ByteUtils.convertMultiByteToInt(temperatureBytes);
		int[] humidityBytes = {data[2], data[3]};
		int humidity = ByteUtils.convertMultiByteToInt(humidityBytes);
		Sht sht = new Sht(temperature, humidity);
		return sht.calculate();
	}
}
