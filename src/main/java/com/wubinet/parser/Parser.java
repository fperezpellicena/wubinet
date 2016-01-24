package com.wubinet.parser;

import com.wubinet.model.MeasureType;

import java.math.BigDecimal;
import java.util.Map;

public interface Parser {

	Map<MeasureType, BigDecimal> parse();

}