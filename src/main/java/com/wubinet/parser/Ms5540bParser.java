package com.wubinet.parser;

import com.rapplogic.xbee.util.ByteUtils;
import com.wubinet.model.MeasureType;
import com.wubinet.model.Ms5540b;

import java.util.Map;

public class Ms5540bParser implements Parser {

	private final int[] data;

	public Ms5540bParser(int[] data) {
		this.data = data;
	}

	public static Ms5540bParser build(int[] data) {
		return new Ms5540bParser(data);
	}

	public Map<MeasureType, Object> parse() {
		int[] coefficients = {data[0], data[1], data[2], data[3], data[4], data[5]};
		int[] pressureBytes = {data[6], data[7]};
		int pressure = ByteUtils.convertMultiByteToInt(pressureBytes);
		int[] temperatureBytes = {data[8], data[9]};
		int temperature = ByteUtils.convertMultiByteToInt(temperatureBytes);
		Ms5540b ms5540b = new Ms5540b(coefficients, pressure, temperature);
		return ms5540b.calculate();
	}
}
