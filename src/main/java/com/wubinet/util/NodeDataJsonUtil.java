package com.wubinet.util;

import com.wubinet.model.MeasureType;
import com.wubinet.model.NodeData;
import com.wubinet.model.SensorType;
import com.wubinet.rest.PlotEntry;
import com.wubinet.rest.PlotEntryKey;
import com.wubinet.service.NodeStats;

import java.math.BigDecimal;
import java.util.*;

import static java.util.stream.Collectors.joining;

public class NodeDataJsonUtil {

	public static String jsonPlotEntries(List<NodeData> data) {
		List<PlotEntry> plotEntries = new ArrayList<>();
		data.forEach(nodeData -> plotEntries.add(buildPlotEntry(nodeData)));
		return plotEntries.stream().map(entry -> entry.toJson()).collect(joining(",", "[", "]"));
	}

	private static PlotEntry buildPlotEntry(NodeData nodeData) {
		PlotEntry plotEntry = new PlotEntry();
		plotEntry.setTimestamp(nodeData.getTimestamp());
		nodeData.getMeasures().forEach(measure -> {
			measure.getValues().keySet().forEach(measureType -> {
				PlotEntryKey key = new PlotEntryKey();
				key.setType(measure.getSensor());
				key.setMeasureType(measureType);
				Object value = measure.getMeasureTypeValue(measureType);
				plotEntry.addSensorMeasure(key, value);
			});
		});
		return plotEntry;
	}

	public static String jsonStatEntries(List<NodeData> nodesData, SensorType sensorType, MeasureType measureType) {
		List<BigDecimal> values = new ArrayList<>();
		for (NodeData nodeData: nodesData) {
			values.addAll(nodeData.extractValues(sensorType, measureType));
		}
		NodeStats nodeStats = buildNodeStats(values, sensorType, measureType);
		return nodeStats.toJson();
	}

	public static NodeStats buildNodeStats(List<BigDecimal> values, SensorType sensorType, MeasureType measureType) {
		NodeStats nodeStats = new NodeStats();
		nodeStats.setSensor(sensorType);
		nodeStats.setMeasureType(measureType);
		nodeStats.setMax(calculateMax(values));
		nodeStats.setMin(calculateMin(values));
		nodeStats.setAvg(calculateAvg(values));
		return nodeStats;
	}

	private static BigDecimal calculateMax(List<BigDecimal> values) {
		Optional<BigDecimal> max = values.stream().max(Comparator.comparing(v -> v));
		if (max.isPresent()) {
			return max.get();
		}
		return BigDecimal.ZERO;
	}

	private static BigDecimal calculateMin(List<BigDecimal> values) {
		Optional<BigDecimal> min = values.stream().min(Comparator.comparing(v -> v));
		if (min.isPresent()) {
			return min.get();
		}
		return BigDecimal.ZERO;
	}

	private static BigDecimal calculateAvg(List<BigDecimal> values) {
		OptionalDouble average = values.stream().mapToDouble(v -> v.doubleValue()).average();
		if (average.isPresent()) {
			return BigDecimal.valueOf(average.getAsDouble()).setScale(2, BigDecimal.ROUND_HALF_UP);
		}
		return BigDecimal.ZERO;
	}
}
