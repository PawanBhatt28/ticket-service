package com.kapture.ticketservice.service;

import java.sql.Timestamp;
import java.util.List;
import org.springframework.stereotype.Service;
import com.kapture.ticketservice.dao.TicketRepository;
import com.kapture.ticketservice.dto.TicketDTO;
import com.kapture.ticketservice.entity.Ticket;
import com.kapture.ticketservice.util.TicketMapper;

@Service
public class TicketService{
	
	private TicketRepository ticketRepository;
	private TicketMapper ticketmapper;
	
	public TicketService(TicketRepository ticketRepository, TicketMapper ticketMapper) {
		this.ticketRepository = ticketRepository;
		this.ticketmapper = ticketMapper;
	}
	
	public Ticket saveTicket(TicketDTO ticketDTO) {
		Ticket ticket = ticketmapper.map(ticketDTO);
		ticket.setLastModifiedDate(new Timestamp(System.currentTimeMillis()));
		return ticketRepository.saveTicket(ticket);
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