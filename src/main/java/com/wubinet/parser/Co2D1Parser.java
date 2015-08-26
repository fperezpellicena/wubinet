package com.wubinet.parser;

import com.rapplogic.xbee.util.ByteUtils;
import com.wubinet.model.Co2D1;
import com.wubinet.model.MeasureType;

import java.util.Map;

public class Co2D1Parser implements Parser {

	private final int[] data;

	public Co2D1Parser(int[] data) {
		this.data = data;
	}

	public static Co2D1Parser build(int[] data) {
		return new Co2D1Parser(data);
	}

	public Map<MeasureType, Object> parse() {
		int[] co2Bytes = {data[0], data[1]};
		int co2 = ByteUtils.convertMultiByteToInt(co2Bytes);
		Co2D1 co2D1 = new Co2D1(co2);
		return co2D1.calculate();
	}
}
