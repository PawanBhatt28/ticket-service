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
public class TicketserviceApplication implements CommandLineRunner{

	@Autowired
	private TicketRepository ticketRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(TicketserviceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		Ticket t = new Ticket();
//		t.setClientId(9);
//		t.setTicket_code(7);
//		t.setStatus("a");
//		t.setTitle("b");
//		ticketRepository.saveTicket(t);
//		TicketDTO ticket = new TicketDTO();
//		ticket.setClientId(2);
//		ticket.setTicketCode(2);
//		ticket.setStatus("x");
//		ticket.setTitle("x");
//		ticketRepository.updateTicket(ticket, 0);
//		ticketRepository.deleteTicket(5);
		
	}

	

	

}
