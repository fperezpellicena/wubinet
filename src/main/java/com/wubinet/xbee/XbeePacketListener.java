package com.wubinet.xbee;

import com.rapplogic.xbee.api.PacketListener;
import com.rapplogic.xbee.api.XBeeResponse;
import com.wubinet.service.NodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

 @Service
public class XbeePacketListener implements PacketListener {

	 @Autowired private NodeService nodeService;

	// FIXME Set thread pool size in configuration
	private final ExecutorService executorService = Executors.newFixedThreadPool(100);

	@Override
	public void processResponse(XBeeResponse response) {
		executorService.execute(new XbeePacketProcessor(response, nodeService));
	}
}
