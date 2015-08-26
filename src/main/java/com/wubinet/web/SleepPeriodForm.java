package com.wubinet.web;

import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Setter @ToString
public class SleepPeriodForm {

	@Min(value = 10)
	@Max(value = 990)
	private int millisSleepPeriod;

	@Min(value = 0)
	@Max(value = 59)
	private int secondsSleepPeriod;

	@Min(value = 0)
	@Max(value = 59)
	private int minutesSleepPeriod;

	@Min(value = 0)
	@Max(value = 3)
	private int hoursSleepPeriod;

	public int sleepPeriod() {
		int hoursInMillis = hoursSleepPeriod * 60 * 60 * 1000;
		int minutesInMillis = minutesSleepPeriod * 60 * 1000;
		int secondsInMillis = secondsSleepPeriod * 1000;
		return (hoursInMillis + minutesInMillis + secondsInMillis + millisSleepPeriod) / 10;
	}
}
