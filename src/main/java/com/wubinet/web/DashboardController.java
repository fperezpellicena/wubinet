package com.wubinet.web;

import com.wubinet.service.ConfigurationService;
import com.wubinet.service.NodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class DashboardController {

	@Autowired private NodeService nodeService;
	@Autowired private ConfigurationService configurationService;

	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public String dashboard(Model model) {
		model.addAttribute("nodes", nodeService.loadNodes());
		return "dashboard";
	}

	@RequestMapping(value = "/dashboard/{address}", method = RequestMethod.GET)
	public String nodeDashboard(@PathVariable String address, Model model) {
		model.addAttribute("nodes", nodeService.loadNodes());
		model.addAttribute("node", nodeService.loadNode(address));
		model.addAttribute("configuration", configurationService.getConfiguration(address));
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
