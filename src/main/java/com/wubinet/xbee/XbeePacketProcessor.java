package com.wubinet.xbee;

import com.rapplogic.xbee.api.ApiId;
import com.rapplogic.xbee.api.XBeeAddress64;
import com.rapplogic.xbee.api.XBeeResponse;
import com.rapplogic.xbee.api.zigbee.ZNetRxResponse;
import com.wubinet.model.*;
import com.wubinet.parser.Parser;
import com.wubinet.service.NodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.wubinet.model.SensorType.parse;
import static com.wubinet.parser.ParserFactory.parserFor;
import static java.util.Arrays.copyOfRange;

public class XbeePacketProcessor implements Runnable {

	private static final Logger LOG = LoggerFactory.getLogger(XbeePacketProcessor.class);

	private final XBeeResponse response;
	private final NodeService nodeService;

	public XbeePacketProcessor(XBeeResponse response, NodeService nodeService) {
		this.response = response;
		this.nodeService = nodeService;
	}

	@Override
	public void run() {
		ApiId apiId = response.getApiId();
		if (ApiId.ZNET_RX_RESPONSE == apiId) {
			ZNetRxResponse zNetRxResponse = (ZNetRxResponse) response;
			int[] packet = zNetRxResponse.getData();
			XBeeAddress64 address = zNetRxResponse.getRemoteAddress64();
			Node node = parseNodeData(packet, address);
			NodeData nodeData = parsePacketData(packet, address);
			nodeService.save(node, nodeData);
			LOG.info("Packet received: " + node);
		}
		// TODO Parse ZNET_IO_NODE_IDENTIFIER_RESPONSE
	}

	private Node parseNodeData(int[] packet, XBeeAddress64 address) {
		Node node = new Node();
		node.setAddress(address.toString());
		node.setMobile(parseMobile(packet));
		return node;
	}

	private NodeData parsePacketData(int[] packet, XBeeAddress64 address) {
		NodeData nodeData = new NodeData();
		nodeData.setMeasures(parseMeasures(packet));
		nodeData.setAddress(address.toString());
		return nodeData;
	}

	private boolean parseMobile(int[] packet) {
		return packet[0] == 1 ? true : false;
	}

	private List<Measure> parseMeasures(int[] packet) {
		List<Measure> measures = new ArrayList<>();
		int from = packet[1] + 2;
		for(SensorType sensorType : parseSensors(packet)) {
			int to = from + sensorType.getDataLength();
			int[] sensorData = copyOfRange(packet, from, to);
			Map<MeasureType, Object> values = parseSensorData(sensorType, sensorData);
			measures.add(buildMeasure(sensorType, values));
			from = to;
		}
		return measures;
	}

	private Map<MeasureType, Object> parseSensorData(SensorType sensorType, int[] sensorData) {
		Parser parser = parserFor(sensorType, sensorData);
		return parser.parse();
	}

	private Measure buildMeasure(SensorType sensorType, Map<MeasureType, Object> values) {
		Measure measure = new Measure();
		measure.setSensor(sensorType);
		measure.setValues(values);
		return measure;
	}

	private List<SensorType> parseSensors(int[] packet) {
		int sensorNumber = packet[1];
		List<SensorType> sensors = new ArrayList<>();
		for (int i = 1; i < sensorNumber + 1; i++) {
			SensorType sensorType = parse(packet[i + 1]);
			sensors.add(sensorType);
		}
		return sensors;
	}
}
