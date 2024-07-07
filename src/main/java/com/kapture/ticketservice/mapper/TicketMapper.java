package com.kapture.ticketservice.mapper;

import com.kapture.ticketservice.dto.TicketDTO;
import com.kapture.ticketservice.entity.Ticket;
import org.springframework.stereotype.Component;

@Component
public class TicketMapper {
	public Ticket map(TicketDTO ticketDTO){
		Ticket ticket = new Ticket();
		ticket.setClientId(ticketDTO.getClientId());
		ticket.setStatus(ticketDTO.getStatus());
		ticket.setTitle(ticketDTO.getTitle());
		return ticket;
	}
}
