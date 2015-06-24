package com.wubinet.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Setter @Getter @ToString
public class NodeData {

	@Id
	private String id;

	private String address;

	private boolean mobile;

	private Date timestamp = new Date();

	private List<Measure> measures = new ArrayList<>();

	public void addMeasure(Measure measure) {
		measures.add(measure);
	}
}
