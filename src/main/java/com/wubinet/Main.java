package com.wubinet;

import com.rapplogic.xbee.api.XBeeException;
import com.wubinet.service.NetworkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main  implements CommandLineRunner {

	@Autowired private NetworkService networkService;

	public static void main(String[] args) throws XBeeException {
		SpringApplication.run(Main.class, args);
	}

	@Override
	public void run(String... strings) throws Exception {
		networkService.open();
	}
}
