package com.kapture.ticketservice.dao;

import java.util.List;

import com.kapture.ticketservice.dto.TicketDTO;
import com.kapture.ticketservice.entity.Ticket;

public interface TicketRepository {
	public Ticket saveTicket(Ticket ticket);
	public List<Ticket> findAllTickets();
	public List<Ticket> getTicket(TicketDTO ticketDTO);
	public Object getTicketByIndex(int clientId, int ticketCode);
	public void updateTicket(TicketDTO ticketDTO, int flag);
	public void deleteTicket(int ticketCode);
	public void deleteTicket(int ticketCode, int clientId);
}
