package com.wubinet;

import com.rapplogic.xbee.api.XBeeException;
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

@SpringBootApplication
@EnableCaching
public class Main  implements CommandLineRunner {

	@Autowired private XBeeService networkService;

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
		networkService.open();
	}
}
