package com.wubinet.web;

import com.wubinet.service.ConfigurationService;
import com.wubinet.service.NodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.Valid;

@Controller
public class ConfigurationController {

	@Autowired private ConfigurationService configurationService;
	@Autowired private NodeService nodeService;

	@RequestMapping(value = "/network-configuration", method = RequestMethod.GET)
	public String configuration(Model model) {
		model.addAttribute("nodes", nodeService.loadNodes());
		return "network-configuration";
	}

	@RequestMapping(value = "/saveSleepPeriod", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public String saveSleepPeriod(@Valid SleepPeriodForm form) {
		boolean result = configurationService.setSleepPeriod(form.sleepPeriod());
		return json(result);
	}

	@RequestMapping(value = "/saveWakeTime", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public String saveWakeTime(@Valid WakeTimeForm form) {
		boolean result = configurationService.setWakeTime(form.wakeTime());
		return json(result);
	}

	private String json(boolean result) {
		return result ? "{status:ok}" : "{status:error}";
	}
}
