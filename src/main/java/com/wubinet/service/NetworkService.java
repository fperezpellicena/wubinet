package com.wubinet.service;

import com.rapplogic.xbee.api.*;
import com.rapplogic.xbee.util.ByteUtils;
import com.wubinet.ConfigProperties;
import com.wubinet.xbee.XbeePacketListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NetworkService {

	private static final Logger LOG = LoggerFactory.getLogger(NetworkService.class);

	private static final String SLEEP_PERIOD = "SP";
	private static final String WAKE_TIME = "ST";
	private static final String SLEEP_MODE = "SM";
	private static final String POWER_LEVEL = "PL";

	@Autowired private XbeePacketListener xbeePacketListener;

	private XBee xbee = new XBee();

	public void open() throws XBeeException {
		xbee.open(ConfigProperties.xbeePort(), ConfigProperties.xbeeBaudrate());
		xbee.addPacketListener(xbeePacketListener);
	}

	public boolean setSleepPeriod(int sleepPeriod) {
		return sendAtCommand(SLEEP_PERIOD, sleepPeriod);
	}

	public boolean setWakeTime(int wakeTime) {
		return sendAtCommand(WAKE_TIME, wakeTime);
	}

	private boolean sendAtCommand(String command, int value) {
		try {
			int[] arrayValue = ByteUtils.convertInttoMultiByte(value);
			AtCommand atCommand = new AtCommand(command, arrayValue);
			XBeeResponse response = xbee.sendSynchronous(atCommand);
			return !response.isError();
		} catch (XBeeException e) {
			return false;
		}
	}

	public boolean setSleepMode(XBeeAddress64 address, int mode) {
		try {
			RemoteAtRequest atRequest = new RemoteAtRequest(address, SLEEP_MODE, mode);
			XBeeResponse response = xbee.sendSynchronous(atRequest);
			return !response.isError();
		} catch (XBeeException e) {
			return false;
		}
	}

	public boolean setPowerLevel(XBeeAddress64 address, int level) {
		try {
			RemoteAtRequest atRequest = new RemoteAtRequest(address, POWER_LEVEL, level);
			XBeeResponse response = xbee.sendSynchronous(atRequest);
			return !response.isError();
		} catch (XBeeException e) {
			return false;
		}
	}

	public NodeConfiguration getConfiguration(XBeeAddress64 address) {
		int[] sleepMode = getSleepMode(address);
		int[] powerLevel = getPowerLevel(address);
		NodeConfiguration configuration = new NodeConfiguration();
		configuration.setSleepMode(sleepMode[0]);
		configuration.setPowerLevel(powerLevel[0]);
		return configuration;
	}

	private int[] getSleepMode(XBeeAddress64 address) {
		try {
			RemoteAtRequest atRequest = new RemoteAtRequest(address, SLEEP_MODE);
			RemoteAtResponse response = (RemoteAtResponse) xbee.sendSynchronous(atRequest);
			if (!response.isError()) {
				return response.getValue();
			} else {
				return new int[1];
			}
		} catch (XBeeException e) {
			return null;
		}
	}

	private int[] getPowerLevel(XBeeAddress64 address) {
		try {
			RemoteAtRequest atRequest = new RemoteAtRequest(address, POWER_LEVEL);
			RemoteAtResponse response = (RemoteAtResponse) xbee.sendSynchronous(atRequest);
			if (!response.isError()) {
				return response.getValue();
			} else {
				return new int[1];
			}
		} catch (XBeeException e) {
			return null;
		}
	}
}
