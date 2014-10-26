package com.wubinet.xbee;

import com.rapplogic.xbee.api.PacketListener;
import com.rapplogic.xbee.api.XBee;
import com.rapplogic.xbee.api.XBeeResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Listener thread for incoming packets.
 */
public class XbeePacketListener implements PacketListener {

	private static final Logger LOG = LoggerFactory.getLogger(XbeePacketListener.class);

	@Override
	public void processResponse(XBeeResponse response) {
		LOG.debug("Packet received");
	}
}
