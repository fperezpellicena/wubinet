package com.wubinet.service.model;

import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
public class SleepPeriod implements Serializable {

	private int sleepPeriod;	// Total amount of milliseconds

	public SleepPeriod(int sleepPeriod) {
		this.sleepPeriod = sleepPeriod * 10;
	}

	public int getSleepPeriodMillis() {
		return sleepPeriod;
	}

	public int getHours() {
		return (sleepPeriod / (1000*60*60)) % 24;
	}

	public int getMinutes() {
		return (sleepPeriod/ (1000*60)) % 60;
	}

	public int getSeconds() {
		return (sleepPeriod / 1000) % 60;
	}

	public int getMilliseconds() {
		return sleepPeriod % 1000;
	}
}
