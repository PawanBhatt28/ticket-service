
package com.kapture.ticketservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = {"com.kapture.ticketservice.*"})
@EnableTransactionManagement
@EnableCaching
public class TicketserviceApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(TicketserviceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
	
	}

	

	

}
