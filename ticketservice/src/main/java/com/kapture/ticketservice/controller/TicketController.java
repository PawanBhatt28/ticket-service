package com.kapture.ticketservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kapture.ticketservice.dao.TicketRepository;
import com.kapture.ticketservice.dto.TicketDTO;
import com.kapture.ticketservice.entity.Ticket;
import com.kapture.ticketservice.service.KafkaServices;

@RestController
public class TicketController {

	@Autowired
	private TicketRepository ticketRepository;

	@Autowired
	private KafkaServices kafkaServices;

	@PostMapping("ticket")
	public Ticket saveTicket(@RequestBody Ticket ticket) {
		return ticketRepository.saveTicket(ticket);
	}

	@PostMapping("tickets")
	public List<Ticket> saveTickets(@RequestBody List<Ticket> tickets) {
		return kafkaServices.produceTicket(tickets);
	}

	@Cacheable(key = "#clientId + '-' + #ticketCode", value = "Ticket")
	@GetMapping("ticket/{clientId}/{ticketCode}")
	public Object getTicket(@PathVariable int clientId, @PathVariable int ticketCode) {
		Object ticket = ticketRepository.getTicketByIndex(clientId, ticketCode);
		return ticket;
	}

	@GetMapping("ticktes")
	public List<Ticket> getTickets(@RequestBody TicketDTO ticketDTO) {
		List<Ticket> ticket = ticketRepository.getTicket(ticketDTO);
		return ticket;
	}

	@PutMapping("update/{flag}")
	public void updateTicket(@RequestBody TicketDTO ticketDTO, @PathVariable int flag) {
		ticketRepository.updateTicket(ticketDTO, flag);
	}

	@CacheEvict(key = "#clientId + '-' + #ticketCode", value = "Ticket")
	@DeleteMapping("delete/{clientId}/{ticketCode}")
	public void deleteTicket(@PathVariable int clientId, @PathVariable int ticketCode) {
		ticketRepository.deleteTicket(clientId, ticketCode);
	}

	@DeleteMapping("delete/{ticketCode}")
	public void deleteTicket(@PathVariable int ticketCode) {
		ticketRepository.deleteTicket(ticketCode);
	}
}
