package com.wubinet.web.form;

import com.wubinet.service.model.NodeConfiguration;
import com.wubinet.web.model.PowerLevel;
import com.wubinet.web.model.SleepMode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter @Setter
@NoArgsConstructor
public class NodeConfigurationForm {

	@NotNull
	private String address;

	private SleepMode sleepMode;

	private PowerLevel powerLevel;

	private String name;

	public NodeConfigurationForm(NodeConfiguration configuration) {
		sleepMode = configuration.getSleepMode();
		powerLevel = configuration.getPowerLevel();
	}

	public NodeConfiguration getNodeConfiguration() {
		NodeConfiguration configuration = new NodeConfiguration();
		configuration.setSleepMode(sleepMode);
		configuration.setPowerLevel(powerLevel);
		configuration.setName(name);
		return configuration;
	}
}
