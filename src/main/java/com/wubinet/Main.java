package com.wubinet;

import com.rapplogic.xbee.api.XBee;
import com.rapplogic.xbee.api.XBeeException;
import com.wubinet.xbee.XbeePacketListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.wubinet.ConfigProperties.xbeeBaudrate;
import static com.wubinet.ConfigProperties.xbeePort;

public class Main {

	private static final Logger LOG = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) throws XBeeException {
		LOG.info("Booting Wubinet service");
		XBee xbee = new XBee();
		xbee.open(xbeePort(), xbeeBaudrate());
		xbee.addPacketListener(new XbeePacketListener());
	}
}
