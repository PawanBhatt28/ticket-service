package com.kapture.ticketservice.util;

import com.kapture.ticketservice.dto.TicketDTO;
import com.kapture.ticketservice.entity.Ticket;

public class TicketMapper {
	public Ticket map(TicketDTO ticketDTO){
		Ticket ticket = new Ticket();
		ticket.setClientId(ticketDTO.getClientId());
		ticket.setStatus(ticketDTO.getStatus());
		ticket.setTicket_code(ticketDTO.getTicketCode());
		ticket.setTitle(ticketDTO.getTitle());
		return ticket;
	}
}
