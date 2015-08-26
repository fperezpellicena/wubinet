package com.wubinet.web;

import com.wubinet.dao.NodeDataRepository;
import com.wubinet.model.NodeData;
import com.wubinet.rest.PlotEntry;
import com.wubinet.rest.PlotEntryKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.joining;

@Controller
public class NodeController {

	@Autowired private NodeDataRepository nodeDataRepository;

	/**
	 *   timestamp  			sht temp  sth %hum  irca temp  irca %co2
	 *   01/01/2015 12:00:00  	27.0      40%       30.3       10%
	 *   01/01/2015 12:00:10	27.0      40%       30.3       10%
	 *
	 */
	@RequestMapping(value = "/{nodeAddress}/measures", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public String measures(@PathVariable String nodeAddress) {
		List<PlotEntry> plotEntries = new ArrayList();
		List<NodeData> nodeDatas = nodeDataRepository.findFirst50ByAddressOrderByTimestampDesc(nodeAddress);
		nodeDatas.forEach(nodeData -> plotEntries.add(buildPlotEntry(nodeData)));
		return jsonPlotEntries(plotEntries);
	}

	private String jsonPlotEntries(List<PlotEntry> plotEntries) {
		return plotEntries.stream().map(entry -> entry.toJson()).collect(joining(",", "[", "]"));
	}

	private PlotEntry buildPlotEntry(NodeData nodeData) {
		PlotEntry plotEntry = new PlotEntry();
		plotEntry.setTimestamp(nodeData.getTimestamp());
		nodeData.getMeasures().forEach(measure -> {
			measure.getValues().keySet().forEach(measureType -> {
				PlotEntryKey key = new PlotEntryKey();
				key.setType(measure.getSensor());
				key.setMeasureType(measureType);
				Object value = measure.getMeasureTypeValue(measureType);
				plotEntry.addSensorMeasure(key, value);
			});
		});
		return plotEntry;
	}
}
