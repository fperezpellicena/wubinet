package com.wubinet.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.util.*;

import static java.util.stream.Collectors.toList;

@Setter @Getter @ToString
public class NodeData {

	@Id
	private String id;

	private String address;

	private Date timestamp = new Date();

	private List<Measure> measures = new ArrayList<>();

	public void addMeasure(Measure measure) {
		measures.add(measure);
	}

	public List<BigDecimal> extractValues(SensorType sensorType, MeasureType measureType) {
		List<BigDecimal> values = measures.stream().
			filter(m -> m.getSensor().equals(sensorType)).
			map(m -> m.getValues()).
			map(v -> v.get(measureType)).
			collect(toList());
		return values;
	}
}
