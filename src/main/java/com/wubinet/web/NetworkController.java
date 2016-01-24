package com.wubinet.web;

import com.wubinet.service.ConfigurationService;
import com.wubinet.web.form.SleepPeriodForm;
import com.wubinet.web.form.WakeTimeForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
public class NetworkController {

	@Autowired private ConfigurationService configurationService;

	@RequestMapping(value = "/network/configuration", method = RequestMethod.GET)
	public String configuration(Model model) {
		model.addAttribute("sleepPeriod", new SleepPeriodForm(configurationService.getSleepPeriod()));
		model.addAttribute("wakeTime", new WakeTimeForm(configurationService.getWakeTime()));
		return "network-configuration";
	}

	@RequestMapping(value = "/network/saveSleepPeriod", method = RequestMethod.POST)
	public String saveSleepPeriod(@Valid SleepPeriodForm form) {
		configurationService.setSleepPeriod(form.sleepPeriod());
		return "redirect:/";
	}

	@RequestMapping(value = "/network/saveWakeTime", method = RequestMethod.POST)
	public String saveWakeTime(@Valid WakeTimeForm form) {
		configurationService.setWakeTime(form.wakeTime());
		return "redirect:/";
	}

}
