package com.wubinet.service.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter
public class NetworkConfiguration implements Serializable {

	private SleepPeriod sleepPeriod;

	private WakeTime wakeTime;
}
