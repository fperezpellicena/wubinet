package com.wubinet.service;

import com.rapplogic.xbee.api.XBeeAddress64;
import com.wubinet.service.model.NodeConfiguration;
import com.wubinet.service.model.SleepPeriod;
import com.wubinet.service.model.WakeTime;
import com.wubinet.util.AddressFormatter;
import com.wubinet.web.model.PowerLevel;
import com.wubinet.web.model.SleepMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class ConfigurationService {

	@Autowired private XBeeService xBeeService;

	@Cacheable("node-configuration")
	public NodeConfiguration getNodeConfiguration(String address) {
		XBeeAddress64 remoteAddress = AddressFormatter.format(address);
		return xBeeService.getNodeConfiguration(remoteAddress);
	}

	@CachePut("node-configuration")
	public void setNodeConfiguration(String address, NodeConfiguration configuration) {
		setSleepMode(address, configuration.getSleepMode());
		setPowerLevel(address, configuration.getPowerLevel());
		setName(address, configuration.getName());
	}

	@Cacheable("sleep-period")
	public SleepPeriod getSleepPeriod() {
		return xBeeService.getSleepPeriod();
	}

	@CachePut("sleep-period")
	public boolean setSleepPeriod(SleepPeriod sleepPeriod) {
		return xBeeService.setSleepPeriod(sleepPeriod.getSleepPeriodMillis());
	}

	@Cacheable("wake-time")
	public WakeTime getWakeTime() {
		return xBeeService.getWakeTime();
	}

	@CachePut("wake-time")
	public boolean setWakeTime(WakeTime wakeTime) {
		return xBeeService.setWakeTime(wakeTime.getWakeTime());
	}

	private boolean setSleepMode(String address, SleepMode mode) {
		XBeeAddress64 remoteAddress = AddressFormatter.format(address);
		return xBeeService.setSleepMode(remoteAddress, mode);
	}

	private boolean setPowerLevel(String address, PowerLevel level) {
		XBeeAddress64 remoteAddress = AddressFormatter.format(address);
		return xBeeService.setPowerLevel(remoteAddress, level);
	}

	private boolean setName(String address, String name) {
		XBeeAddress64 remoteAddress = AddressFormatter.format(address);
		return xBeeService.setName(remoteAddress, name);
	}
}
