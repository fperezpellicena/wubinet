package com.wubinet;

import com.rapplogic.xbee.api.XBee;
import com.rapplogic.xbee.api.XBeeException;
import com.wubinet.xbee.XbeePacketListener;
import gnu.io.CommPortIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Enumeration;

public class Main {

	private static final Logger LOG = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) throws XBeeException {
		LOG.info("Booting Wubinet service");
		XBee xbee = new XBee();
		xbee.open("/dev/ttyUSB0", 9600);
		xbee.addPacketListener(new XbeePacketListener());
	}
}
