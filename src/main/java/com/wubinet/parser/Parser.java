package com.wubinet.parser;

import com.wubinet.model.MeasureType;

import java.util.Map;

public interface Parser {

	Map<MeasureType, Object> parse();
}
