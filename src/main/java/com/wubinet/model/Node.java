package com.wubinet.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Setter @Getter @ToString
public class Node {

	@Id
	private String address;

	private String name;

	private boolean mobile;

	private List<SensorType> sensorTypes;

	public String getFormattedAddress() {
		List<String> parts = Arrays.asList(address.split(","));
		return parts.stream().map(p -> p.replace("0x", "")).collect(Collectors.joining("-"));
	}

	public List<NodeSensors> getSensorTypeMeasures() {
		return sensorTypes.stream().
			map(sensorType -> SensorType.nodeSensors(sensorType)).
			collect(toList());
	}
}
