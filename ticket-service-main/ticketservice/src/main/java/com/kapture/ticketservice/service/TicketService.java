package com.kapture.ticketservice.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import com.kapture.ticketservice.dao.TicketRepository;
import com.kapture.ticketservice.dto.TicketDTO;
import com.kapture.ticketservice.entity.Ticket;
import com.kapture.ticketservice.util.TicketMapper;

@Service
public class TicketService{
	
	private TicketRepository ticketRepository;
	private TicketMapper ticketmapper;
	private KafkaServices kafkaServices;

	public TicketService(TicketRepository ticketRepository, TicketMapper ticketMapper, KafkaServices kafkaServices) {
		this.ticketRepository = ticketRepository;
		this.ticketmapper = ticketMapper;
		this.kafkaServices = kafkaServices;
	}
	
	public Ticket saveTicket(TicketDTO ticketDTO) {
		Ticket ticket = ticketmapper.map(ticketDTO);
		ticket.setLastModifiedDate(new Timestamp(System.currentTimeMillis()));
		System.out.println(ticket.toString());
		return ticketRepository.saveTicket(ticket);
	}
	
	public List<Ticket> saveTicket(List<TicketDTO> ticketsDTO){
		List<Ticket> tickets = ticketsDTO.stream().map(ticketDTO ->{
			return ticketmapper.map(ticketDTO);
		}).collect(Collectors.toList());
		List<Ticket>savedTickets = kafkaServices.produceTicket(tickets);
		return savedTickets;
	}
	
	public Object getTicket(int clientId, int ticketCode) {
		Object ticket =  ticketRepository.getTicketByIndex(clientId, ticketCode);
		return ticket;
	}
	
	
	public List<Ticket> getTickets(TicketDTO ticketDTO) {
		List<Ticket> tickets = ticketRepository.getTicket(ticketDTO);
		return tickets;
	}
	
	
	public Ticket updateTicket(TicketDTO ticketDTO) {
		return ticketRepository.updateTicket(ticketDTO);
	}
}