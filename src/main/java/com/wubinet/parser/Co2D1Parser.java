package com.wubinet.parser;

import com.rapplogic.xbee.util.ByteUtils;
import com.wubinet.model.Co2D1;
import com.wubinet.model.MeasureType;

import java.math.BigDecimal;
import java.util.Map;

public class Co2D1Parser implements Parser {

	private final int[] data;

	public Co2D1Parser(int[] data) {
		this.data = data;
	}

	public static Co2D1Parser build(int[] data) {
		return new Co2D1Parser(data);
	}

	@Override
	public Map<MeasureType, BigDecimal> parse() {
		int[] co2Bytes = {data[0], data[1]};
		int co2 = ByteUtils.convertMultiByteToInt(co2Bytes);
		int[] referenceBytes = {data[2], data[3]};
		int reference = ByteUtils.convertMultiByteToInt(referenceBytes);
		int[] temperatureBytes = {data[4], data[5]};
		int temperature = ByteUtils.convertMultiByteToInt(temperatureBytes);
		int[] offsetBytes = {data[6], data[7]};
		int offset = ByteUtils.convertMultiByteToInt(offsetBytes);
		Co2D1 co2D1 = new Co2D1(reference, offset, co2, temperature);
		return co2D1.calculate();
	}
}
