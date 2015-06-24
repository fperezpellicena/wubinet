package com.wubinet;

import com.rapplogic.xbee.api.XBee;
import com.rapplogic.xbee.api.XBeeException;
import com.wubinet.xbee.XbeePacketListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main  implements CommandLineRunner {

	private static final Logger LOG = LoggerFactory.getLogger(Main.class);

	@Autowired private XbeePacketListener xbeePacketListener;

	public static void main(String[] args) throws XBeeException {
		SpringApplication.run(Main.class, args);
	}

	@Override
	public void run(String... strings) throws Exception {
		LOG.info("Booting Wubinet service");
		XBee xbee = new XBee();
		xbee.open(ConfigProperties.xbeePort(), ConfigProperties.xbeeBaudrate());
		xbee.addPacketListener(xbeePacketListener);
	}
}
