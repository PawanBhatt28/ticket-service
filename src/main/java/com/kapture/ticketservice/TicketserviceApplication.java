package com.kapture.ticketservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = {"com.kapture.ticketservice.*"})
@EnableTransactionManagement
public class TicketserviceApplication{
	public static void main(String[] args) {
		SpringApplication.run(TicketserviceApplication.class, args);
	}
}
