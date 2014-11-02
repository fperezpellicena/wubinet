package com.wubinet.xbee;

import com.rapplogic.xbee.api.PacketListener;
import com.rapplogic.xbee.api.XBeeResponse;
import com.wubinet.ConfigProperties;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Listener thread for incoming packets.
 */
public class XbeePacketListener implements PacketListener {

	// Rest template is thread safe
	private static final RestTemplate REST_TEMPLATE = new RestTemplate();
	private static final String URL = ConfigProperties.serverEndpointUrl();
	// FIXME Set thread pool size in configuration
	private final ExecutorService executorService = Executors.newFixedThreadPool(100);

	@Override
	public void processResponse(XBeeResponse response) {
		executorService.execute(new XbeePacketProcessor(response, URL, REST_TEMPLATE));
	}
}
