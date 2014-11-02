package com.wubinet.xbee;

import com.rapplogic.xbee.api.ApiId;
import com.rapplogic.xbee.api.XBeeResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.springframework.http.HttpMethod.POST;

public class XbeePacketProcessor implements Runnable {

	private static final Logger LOG = LoggerFactory.getLogger(XbeePacketProcessor.class);

	private final XBeeResponse response;
	private final RestTemplate restTemplate;
	private final String url;

	public XbeePacketProcessor(XBeeResponse response, String url, RestTemplate restTemplate) {
		this.response = response;
		this.url = url;
		this.restTemplate = restTemplate;
	}

	@Override
	public void run() {
		LOG.debug("Packet received");
		ApiId apiId = response.getApiId();
		if (ApiId.RX_64_RESPONSE == apiId) {
			LOG.debug("Received RX_64_RESPONSE");
			postReceivedPacket();
		}
	}

	private void postReceivedPacket() {
		HttpEntity<XBeeResponse> requestEntity = new HttpEntity<>(response);
		ResponseEntity<String> responseEntity = restTemplate.exchange(url, POST, requestEntity, String.class);
		if (!responseEntity.getStatusCode().is2xxSuccessful()) {
			LOG.error("The packet could not be sent to the server due to: " +
					responseEntity.getStatusCode().getReasonPhrase());
		}
	}
}
