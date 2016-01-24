package com.wubinet.web.form;

import com.wubinet.service.model.SleepPeriod;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Getter @Setter
@NoArgsConstructor
@ToString
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

	public SleepPeriodForm(SleepPeriod sleepPeriod) {
		this.millisSleepPeriod = sleepPeriod.getMilliseconds() == 0 ? 10 : sleepPeriod.getMilliseconds();
		this.secondsSleepPeriod = sleepPeriod.getSeconds();
		this.minutesSleepPeriod = sleepPeriod.getMinutes();
		this.hoursSleepPeriod = sleepPeriod.getHours();
	}

	public SleepPeriod sleepPeriod() {
		int hoursInMillis = hoursSleepPeriod * 60 * 60 * 1000;
		int minutesInMillis = minutesSleepPeriod * 60 * 1000;
		int secondsInMillis = secondsSleepPeriod * 1000;
		int sleepPeriodMillis = (hoursInMillis + minutesInMillis + secondsInMillis + millisSleepPeriod) / 10;
		return new SleepPeriod(sleepPeriodMillis);
	}
}
