package com.wubinet.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;

@Setter @Getter @ToString
public class Node {

	@Id
	private String address;

	private boolean mobile;
}
