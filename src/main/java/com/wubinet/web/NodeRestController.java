package com.wubinet.web;

import com.wubinet.dao.NodeDataRepository;
import com.wubinet.model.MeasureType;
import com.wubinet.model.NodeData;
import com.wubinet.model.SensorType;
import com.wubinet.service.ConfigurationService;
import com.wubinet.service.model.SleepPeriod;
import com.wubinet.util.NodeDataJsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class NodeRestController {

	@Autowired private NodeDataRepository nodeDataRepository;

	@Autowired private ConfigurationService configurationService;

	@RequestMapping(value = "/{nodeAddress}/measures", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public String measures(@PathVariable String nodeAddress) {
		List<NodeData> data = nodeDataRepository.findLast50ByAddressOrderByTimestampAsc(nodeAddress);
		return NodeDataJsonUtil.jsonPlotEntries(data);
	}

	@RequestMapping(value = "/{nodeAddress}/stats", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public String stats(@PathVariable String nodeAddress, @RequestParam SensorType sensorType,
			@RequestParam MeasureType measureType) {
		List<NodeData> data = nodeDataRepository.findLast50ByAddressOrderByTimestampAsc(nodeAddress);
		return NodeDataJsonUtil.jsonStatEntries(data, sensorType, measureType);
	}

	@RequestMapping(value = "/sleepPeriod", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public String wakeTime() {
		SleepPeriod sleepPeriod = configurationService.getSleepPeriod();
		return "{\"sleep_period\" : \""  + sleepPeriod.getSleepPeriodMillis() + "\"}";
	}
}
