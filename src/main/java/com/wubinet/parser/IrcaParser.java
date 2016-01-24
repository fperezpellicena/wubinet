package com.wubinet.parser;

import com.wubinet.model.Irca;
import com.wubinet.model.MeasureType;

import java.math.BigDecimal;
import java.util.Map;

import static com.rapplogic.xbee.util.ByteUtils.convertMultiByteToInt;

public class IrcaParser implements Parser {

	private final int[] data;

	public IrcaParser(int[] data) {
		this.data = data;
	}

	public static Parser build(int[] data) {
		return new IrcaParser(data);
	}

	@Override
	public Map<MeasureType, BigDecimal> parse() {
		int[] activeBytes = {data[0], data[1]};
		int active = convertMultiByteToInt(activeBytes);
		int[] referenceBytes = {data[2], data[3]};
		int reference = convertMultiByteToInt(referenceBytes);
		int[] temperatureBytes = {data[4], data[5]};
		int temperature = convertMultiByteToInt(temperatureBytes);
		Irca irca = new Irca(active, reference, temperature);
		return irca.calculate();
	}

}
