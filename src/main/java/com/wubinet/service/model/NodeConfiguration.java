package com.wubinet.service.model;

import com.wubinet.web.model.PowerLevel;
import com.wubinet.web.model.SleepMode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter @Setter @ToString
public class NodeConfiguration implements Serializable {

	private SleepMode sleepMode;

	private PowerLevel powerLevel;
}
