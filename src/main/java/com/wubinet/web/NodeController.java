package com.wubinet.web;

import com.wubinet.model.Node;
import com.wubinet.service.ConfigurationService;
import com.wubinet.service.NodeService;
import com.wubinet.service.XBeeService;
import com.wubinet.service.model.NodeConfiguration;
import com.wubinet.web.form.NodeConfigurationForm;
import com.wubinet.web.model.PowerLevel;
import com.wubinet.web.model.SleepMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
public class NodeController {

	@Autowired private NodeService nodeService;

	@Autowired private XBeeService networkService;

	@Autowired private ConfigurationService configurationService;

	@ModelAttribute
	public void defaultModel(Model model) {
		model.addAttribute("menu", "nodes");
	}

	@RequestMapping(value = "/nodes", method = RequestMethod.GET)
	public String nodes(Model model) {
		model.addAttribute("nodes", nodeService.loadNodes());
		return "nodes";
	}

	@RequestMapping(value = "/node/configuration", method = RequestMethod.GET)
	public String nodeConfiguration(@RequestParam String address, Model model){
		NodeConfiguration configuration = configurationService.getNodeConfiguration(address);
		model.addAttribute("configuration", new NodeConfigurationForm(configuration));
		model.addAttribute("node", nodeService.loadNode(address));
		model.addAttribute("sleepModes", SleepMode.values());
		model.addAttribute("powerLevels", PowerLevel.values());
		return "node-configuration";
	}

	@RequestMapping(value = "/node/saveConfiguration", method = RequestMethod.POST)
	public String saveNodeConfiguration(@Valid NodeConfigurationForm form) {
		String address = form.getAddress();
		NodeConfiguration configuration = form.getNodeConfiguration();
		configurationService.setNodeConfiguration(address, configuration);
		return "redirect:/nodes";
	}

	@RequestMapping(value = "/node/nodeChart", method = RequestMethod.GET)
	public String nodeChart(@RequestParam String address, Model model) {
		Node node = nodeService.loadNode(address);
		model.addAttribute("node", node);
		model.addAttribute("sensorTypeMeasures", node.getSensorTypeMeasures());
		model.addAttribute("sleepPeriod", networkService.getSleepPeriod());
		return "node-chart";
	}
}
