package com.wubinet.web;

import com.wubinet.service.ConfigurationService;
import com.wubinet.service.XBeeService;
import com.wubinet.service.NodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

// TODO Remove
@Controller
public class DashboardController {

	@Autowired private NodeService nodeService;
	@Autowired private ConfigurationService configurationService;
	@Autowired private XBeeService networkService;

	@ModelAttribute
	public void defaultModel(Model model) {
		model.addAttribute("menu", "dashboard");
	}

	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public String dashboard(Model model) {
		model.addAttribute("nodes", nodeService.loadNodes());
		return "index";
	}

	@RequestMapping(value = "/dashboard/{address}", method = RequestMethod.GET)
	public String nodeDashboard(@PathVariable String address, Model model) {
		model.addAttribute("nodes", nodeService.loadNodes());
		model.addAttribute("node", nodeService.loadNode(address));
		// TODO Extract this parameters
//		model.addAttribute("configuration", configurationService.getNodeConfiguration(address));
//		model.addAttribute("sleepPeriod", networkService.getSleepPeriod().getSleepPeriod());
		return "dashboard";
	}

	@RequestMapping(value = "/configuration/{address}", method = RequestMethod.GET)
	public String nodeConfiguration(@PathVariable String address, Model model) {
		model.addAttribute("nodes", nodeService.loadNodes());
		model.addAttribute("node", nodeService.loadNode(address));
		return "configuration";
	}

	@RequestMapping(value = "/error", method = RequestMethod.GET)
	public String error() {
		return "error";
	}
}
