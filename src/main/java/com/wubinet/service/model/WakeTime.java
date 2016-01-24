package com.wubinet.service.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
public class WakeTime implements Serializable {

	private int wakeTime;

	public WakeTime(int wakeTime) {
		this.wakeTime = wakeTime;
	}

	public int  getMinutes() {
		return (wakeTime / (1000*60)) % 60;
	}

	public int getSeconds() {
		return (wakeTime / 1000) % 60;
	}

	public int getMilliseconds() {
		return wakeTime % 1000;
	}
}
