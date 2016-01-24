package com.wubinet.web.model;

import lombok.Getter;

@Getter
public enum PowerLevel {

	NORMAL(0, "Normal"),
	SLEEP_SUPPORT(7, "Sleep support"),
	CYCLIC_SLEEP(8, "Cyclic sleep");

	private final int value;

	private final String text;

	PowerLevel(int value, String text) {
		this.value = value;
		this.text = text;
	}

	public static PowerLevel parse(int value) {
		switch (value) {
			case 0:
				return NORMAL;
			case 7:
				return SLEEP_SUPPORT;
			case 8:
				return CYCLIC_SLEEP;
			default:
				throw new EnumConstantNotPresentException(PowerLevel.class, "Power level value not found: " + value);

		}
	}
}
