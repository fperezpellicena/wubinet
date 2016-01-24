package com.wubinet.rest;

import lombok.Getter;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static java.util.stream.Collectors.joining;

@Getter @Setter
public class PlotEntry {

	private Date timestamp;

	private Map<PlotEntryKey, Object> sensorMeasure = new HashMap();

	public void addSensorMeasure(PlotEntryKey key, Object value) {
		sensorMeasure.put(key, value);
	}

	public String toJson() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append(jsonTimestamp());
		sb.append(jsonSensorMeasures());
		sb.append("}");
		return sb.toString();
	}

	private String jsonTimestamp() {
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-YYYY hh:mm:ss");
		return "\"timestamp\":\"" + format.format(timestamp) + "\",";
	}

	private String jsonSensorMeasures() {
		return sensorMeasure.entrySet().stream().
				map(entry -> jsonSensorMeasure(entry)).
				collect(joining(","));
	}

	private String jsonSensorMeasure(Map.Entry<PlotEntryKey, Object> entry) {
		return "\"" + entry.getKey() + "\":\"" + entry.getValue() + "\"";
	}
}
