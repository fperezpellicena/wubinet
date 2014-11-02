package com.wubinet;

import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.Properties;

public class ConfigProperties {

	private static final String PROPERTIES_FILE = "config.properties";

	private static final Properties PROPERTIES = new Properties();

	static {
		try {
			PROPERTIES.load(new ClassPathResource(PROPERTIES_FILE).getInputStream());
		} catch (IOException ex) {
			throw new IllegalStateException("Configuration properties cannot be readed", ex);
		}
	}

	private ConfigProperties() {
	}

	public static String xbeePort() {
		return PROPERTIES.getProperty("xbee.port");
	}

	public static int xbeeBaudrate() {
		return Integer.parseInt(PROPERTIES.getProperty("xbee.baudrate"));
	}

	public static String serverEndpointUrl() {
		return PROPERTIES.getProperty("server.endpointUrl");
	}
}
