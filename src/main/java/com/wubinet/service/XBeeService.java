package com.wubinet.service;

import com.rapplogic.xbee.api.*;
import com.rapplogic.xbee.util.ByteUtils;
import com.wubinet.ConfigProperties;
import com.wubinet.service.model.NodeConfiguration;
import com.wubinet.service.model.SleepPeriod;
import com.wubinet.service.model.WakeTime;
import com.wubinet.web.model.PowerLevel;
import com.wubinet.web.model.SleepMode;
import com.wubinet.xbee.XbeePacketListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class XBeeService {

	private static final Logger LOG = LoggerFactory.getLogger(XBeeService.class);

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

	public boolean setSleepMode(XBeeAddress64 address, SleepMode sleepMode) {
		try {
			RemoteAtRequest atRequest = new RemoteAtRequest(address, SLEEP_MODE, sleepMode.getValue());
			XBeeResponse response = xbee.sendSynchronous(atRequest);
			return !response.isError();
		} catch (XBeeException e) {
			return false;
		}
	}

	public boolean setPowerLevel(XBeeAddress64 address, PowerLevel powerLevel) {
		try {
			RemoteAtRequest atRequest = new RemoteAtRequest(address, POWER_LEVEL, powerLevel.getValue());
			XBeeResponse response = xbee.sendSynchronous(atRequest);
			return !response.isError();
		} catch (XBeeException e) {
			return false;
		}
	}

	public NodeConfiguration getNodeConfiguration(XBeeAddress64 address) {
		int[] sleepMode = getSleepMode(address);
		int[] powerLevel = getPowerLevel(address);
		NodeConfiguration configuration = new NodeConfiguration();
		configuration.setSleepMode(SleepMode.parse(sleepMode[0]));
		configuration.setPowerLevel(PowerLevel.parse(powerLevel[0]));
		return configuration;
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
			return new int[1];
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
			return new int[1];
		}
	}

	public SleepPeriod getSleepPeriod() {
		try {
			AtCommand atRequest = new AtCommand(SLEEP_PERIOD);
			AtCommandResponse response = (AtCommandResponse) xbee.sendSynchronous(atRequest);
			if (!response.isError()) {
				int sleepPeriod = ByteUtils.convertMultiByteToInt(response.getValue());
				return new SleepPeriod(sleepPeriod);
			} else {
				return new SleepPeriod();
			}
		} catch (XBeeException e) {
			return new SleepPeriod();
		}
	}

	public WakeTime getWakeTime() {
		try {
			AtCommand atRequest = new AtCommand(WAKE_TIME);
			AtCommandResponse response = (AtCommandResponse) xbee.sendSynchronous(atRequest);
			if (!response.isError()) {
				int wakeTime = ByteUtils.convertMultiByteToInt(response.getValue());
				return new WakeTime(wakeTime);
			} else {
				return new WakeTime();
			}
		} catch (XBeeException e) {
			return new WakeTime();
		}
	}
}
