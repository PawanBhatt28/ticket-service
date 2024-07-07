package com.kapture.ticketservice.service;

import java.util.List;

import com.kapture.ticketservice.mapper.TicketMapper;
import com.kapture.ticketservice.repository.TicketRepository;
import com.kapture.ticketservice.entity.Ticket;
import com.kapture.ticketservice.dto.TicketDTO;

import org.springframework.beans.factory.annotation.Autowired;

public class TicketService {

    @Autowired
    Ticket ticket;
    @Autowired
    TicketRepository ticketRepository;
    @Autowired
    TicketMapper ticketMapper;

    public List<Ticket> searchTickets(TicketDTO ticketDTO, String status ){
        if(status.equals("all")){
            return ticketRepository.findAllTickets();
        }
        if(status.equals("single")){
            return List.of(ticketRepository.getTicketByIndex(ticketDTO.getClientId(), ticketDTO.getTicketCode())));
        }
        if(status.equals("multiple")){
            return ticketRepository.getTicket(ticketDTO));
        }
    }

    public Ticket addTicket(TicketDTO ticketDTO){
        Ticket ticket = ticketMapper.map(ticketDTO);
        return ticketRepository.saveTicket(ticket);
    }

    public Ticket updateTicket(TicketDTO ticketDTO, int flag){
        return ticketRepository.updateTicket(ticketDTO, flag);
    }

    public Ticket deleteTicket(TicketDTO ticketDTO){
        return ticketRepository.deleteTicket(ticketDTO.getClientId(), ticket.getTicketCode());
    }
}
