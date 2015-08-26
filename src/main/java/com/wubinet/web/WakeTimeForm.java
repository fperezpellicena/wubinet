package com.wubinet.web;

import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Setter
public class WakeTimeForm {

	@Min(value = 0)
	@Max(value = 999)
	private int millisWakeTime;

	@Min(value = 0)
	@Max(value = 59)
	private int secondsWakeTime;

	@Min(value = 0)
	@Max(value = 59)
	private int minutesWakeTime;

	public int wakeTime() {
		int minutesInMillis = minutesWakeTime * 60 * 1000;
		int secondsInMillis = secondsWakeTime * 1000;
		return minutesInMillis + secondsInMillis + millisWakeTime;
	}
}
