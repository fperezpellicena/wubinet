package com.wubinet;

import com.rapplogic.xbee.api.XBeeException;
import com.wubinet.model.*;
import com.wubinet.service.NodeService;
import com.wubinet.service.XBeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

@SpringBootApplication
@EnableCaching
public class Main  implements CommandLineRunner {

	@Autowired private XBeeService networkService;
	@Autowired private NodeService nodeService;

	@Bean
	public CacheManager cacheManager() {
		return new EhCacheCacheManager(ehCacheCacheManager().getObject());
	}

	@Bean
	public EhCacheManagerFactoryBean ehCacheCacheManager() {
		EhCacheManagerFactoryBean cacheManagerFactoy = new EhCacheManagerFactoryBean();
		cacheManagerFactoy.setShared(true); // Shared with Hibernate
		return cacheManagerFactoy;
	}

	public static void main(String[] args) throws XBeeException {
		SpringApplication.run(Main.class, args);
	}

	@Override
	public void run(String... strings) throws Exception {
		Node node = new Node();
		node.setAddress("00:13:A2:00:64:4E:D5:FA");
		node.setMobile(false);
		node.setName("LAB_INST");
		node.setSensorTypes(Arrays.asList(SensorType.SHT11, SensorType.INTEGRITY_SENSOR, SensorType.MS5540B));

		List<Timestamp> instants = oneDayMeasureInstants();
		Map<MeasureType, BigDecimal> values;
		for (Timestamp instant : instants) {
			NodeData data = new NodeData();
			data.setTimestamp(instant);
			data.setAddress("00:13:A2:00:64:4E:D5:FA");
			List<Measure> measures = new ArrayList<>();

			Measure sht11 = new Measure();
			sht11.setSensor(SensorType.SHT11);
			values = new HashMap<>();
			values.put(MeasureType.TEMPERATURE, BigDecimal.valueOf(35));
			values.put(MeasureType.HUMIDITY, BigDecimal.valueOf(45));
			sht11.setValues(values);
			measures.add(sht11);

			Measure integrity = new Measure();
			integrity.setSensor(SensorType.INTEGRITY_SENSOR);
			values = new HashMap<>();
			values.put(MeasureType.TEMPERATURE, BigDecimal.valueOf(30.2));
			values.put(MeasureType.HUMIDITY, BigDecimal.valueOf(28));
			integrity.setValues(values);
			measures.add(integrity);

			Measure ms5540b = new Measure();
			ms5540b.setSensor(SensorType.MS5540B);
			values = new HashMap<>();
			values.put(MeasureType.ATMOSPHERIC_PRESSURE, BigDecimal.valueOf(990));
			values.put(MeasureType.TEMPERATURE, BigDecimal.valueOf(44.5));
			ms5540b.setValues(values);
			measures.add(ms5540b);

			data.setMeasures(measures);
			nodeService.save(node, data);
		}
//		networkService.open();
	}

	// Create timestamps every 15 minutes for one day
	private List<Timestamp> oneDayMeasureInstants() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.AM_PM, Calendar.PM);
		calendar.set(Calendar.YEAR, 2015);
		calendar.set(Calendar.MONTH, Calendar.JUNE);
		calendar.set(Calendar.DAY_OF_MONTH, 14);
		calendar.set(Calendar.HOUR, 10);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		List<Timestamp> instants = new ArrayList<>();
		instants.add(new Timestamp(calendar.getTimeInMillis()));
		for (int i = 0; i < 95; i++) {
			calendar.add(Calendar.MINUTE, 15);
			instants.add(new Timestamp(calendar.getTimeInMillis()));
		}
		System.out.println(instants.size());
		return instants;
	}
}
