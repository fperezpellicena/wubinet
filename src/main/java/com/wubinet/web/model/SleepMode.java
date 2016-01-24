package com.wubinet.web.model;

import lombok.Getter;

@Getter
public enum SleepMode {

	MINIMUM(0, "Minimum"),
	LOW(1, "Low"),
	MEDIUM(2, "Medium"),
	HIGH(3, "High"),
	MAXIMUM(4, "Maximum");

	private final int value;

	private final String text;

	SleepMode(int value, String text) {
		this.value = value;
		this.text = text;
	}

	public static SleepMode parse(int value) {
		switch (value) {
			case 0:
				return MINIMUM;
			case 1:
				return LOW;
			case 2:
				return MEDIUM;
			case 3:
				return HIGH;
			case 4:
				return MAXIMUM;
			default:
				throw new EnumConstantNotPresentException(SleepMode.class, "Sleep mode not found:" + value);
		}
	}
}
