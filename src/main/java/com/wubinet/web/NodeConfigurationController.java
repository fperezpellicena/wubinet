package com.wubinet.web;

import com.wubinet.service.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class NodeConfigurationController {

	@Autowired private ConfigurationService configurationService;

	@RequestMapping(value = "/saveSleepMode", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public String saveSleepMode(String address, int mode) {
		boolean result = configurationService.setSleepMode(address, mode);
		return json(result);
	}

	@RequestMapping(value = "/savePowerLevel", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public String savePowerLevel(String address, int level) {
		boolean result = configurationService.setPowerLevel(address, level);
		return json(result);
	}

	private String json(boolean result) {
		return result ? "{status:ok}" : "{status:error}";
	}
}
