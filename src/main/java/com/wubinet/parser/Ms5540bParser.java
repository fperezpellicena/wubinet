package com.wubinet.parser;

import com.wubinet.model.MeasureType;
import com.wubinet.model.Ms5540b;

import java.math.BigDecimal;
import java.util.Map;

import static com.rapplogic.xbee.util.ByteUtils.convertMultiByteToInt;

public class Ms5540bParser implements Parser {

	private final int[] data;

	public Ms5540bParser(int[] data) {
		this.data = data;
	}

	public static Ms5540bParser build(int[] data) {
		return new Ms5540bParser(data);
	}

	public Map<MeasureType, BigDecimal> parse() {
		int c1 = convertMultiByteToInt(new int[]{data[0], data[1]});
		int c2 = convertMultiByteToInt(new int[]{data[2], data[3]});
		int c3 = convertMultiByteToInt(new int[]{data[4], data[5]});
		int c4 = convertMultiByteToInt(new int[]{data[6], data[7]});
		int c5 = convertMultiByteToInt(new int[]{data[8], data[9]});
		int c6 = convertMultiByteToInt(new int[]{data[10], data[11]});
		int[] coefficients = {c1, c2, c3, c4, c5, c6};
		int[] pressureBytes = {data[12], data[13]};
		int pressure = convertMultiByteToInt(pressureBytes);
		int[] temperatureBytes = {data[14], data[15]};
		int temperature = convertMultiByteToInt(temperatureBytes);
		Ms5540b ms5540b = new Ms5540b(coefficients, pressure, temperature);
		return ms5540b.calculate();
	}
}
