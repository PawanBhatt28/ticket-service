package com.kapture.ticketservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.kapture.ticketservice.dao.TicketDAO;
import com.kapture.ticketservice.dto.TicketDTO;
import com.kapture.ticketservice.entity.Ticket;
import com.kapture.ticketservice.repository.TicketRepository;

@SpringBootApplication(scanBasePackages = {"com.kapture.ticketservice.*"})
@EnableTransactionManagement
public class TicketserviceApplication{
	public static void main(String[] args) {
		SpringApplication.run(TicketserviceApplication.class, args);
	}
}
