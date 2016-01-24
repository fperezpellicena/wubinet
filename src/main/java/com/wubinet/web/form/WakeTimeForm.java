package com.wubinet.web.form;

import com.wubinet.service.model.WakeTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Setter @Getter
@NoArgsConstructor
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

	public WakeTimeForm(WakeTime wakeTime) {
		this.millisWakeTime = wakeTime.getMilliseconds();
		this.secondsWakeTime = wakeTime.getSeconds();
		this.minutesWakeTime = wakeTime.getMinutes();
	}

	public WakeTime wakeTime() {
		int minutesInMillis = minutesWakeTime * 60 * 1000;
		int secondsInMillis = secondsWakeTime * 1000;
		return new WakeTime(minutesInMillis + secondsInMillis + millisWakeTime);
	}
}
