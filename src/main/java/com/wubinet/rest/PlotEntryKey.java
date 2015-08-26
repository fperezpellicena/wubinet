package com.wubinet.rest;

import com.wubinet.model.MeasureType;
import com.wubinet.model.SensorType;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PlotEntryKey {

	private SensorType type;

	private MeasureType measureType;

	@Override
	public String toString() {
		return type.name().toLowerCase() + "_" + measureType.name().toLowerCase();
	}
}
