package com.wubinet.service;

import com.rapplogic.xbee.api.XBeeAddress64;
import com.wubinet.util.AddressFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfigurationService {

	@Autowired private NetworkService networkService;

	public boolean setSleepPeriod(int sleepPeriod) {
		return networkService.setSleepPeriod(sleepPeriod);
	}

	public boolean setWakeTime(int wakeTime) {
		return networkService.setWakeTime(wakeTime);
	}

	public boolean setSleepMode(String address, int mode) {
		XBeeAddress64 remoteAddress = AddressFormatter.format(address);
		return networkService.setSleepMode(remoteAddress, mode);
	}

	public boolean setPowerLevel(String address, int level) {
		XBeeAddress64 remoteAddress = AddressFormatter.format(address);
		return networkService.setPowerLevel(remoteAddress, level);
	}

	public NodeConfiguration getConfiguration(String address) {
		XBeeAddress64 remoteAddress = AddressFormatter.format(address);
		return networkService.getConfiguration(remoteAddress);
	}
}
