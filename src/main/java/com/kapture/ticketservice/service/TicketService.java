package com.kapture.ticketservice.service;

import com.kapture.ticketservice.mapper.TicketMapper;
import com.kapture.ticketservice.repository.TicketRepository;
import com.kapture.ticketservice.dto.ResponseDTO;
import com.kapture.ticketservice.entity.Ticket;
import com.kapture.ticketservice.dto.TicketDTO;

import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;

public class TicketService {

    @Autowired
    Ticket ticket;
    @Autowired
    TicketRepository ticketRepository;
    @Autowired
    ResponseDTO responseDTO;
    @Autowired
    TicketMapper ticketMapper;

    public ResponseDTO searchTickets(TicketDTO ticketDTO, String status ){
        if(status.equals("all")){
            return ticketRepository.findAllTickets();
        }
        if(status.equals("single")){
            return ticketRepository.getTicketByIndex(ticketDTO.getClientId(), ticketDTO.getTicketCode()));
        }
        if(status.equals("multiple")){
            return ticketRepository.getTicket(ticketDTO));
        }
    }

    public ResponseDTO addTicket(TicketDTO ticketDTO){
        ticket = ticketMapper.map(ticketDTO);
        return ticketRepository.saveTicket(ticket);
    }

    public ResponseDTO updateTicket(TicketDTO ticketDTO, int flag){
        return ticketRepository.updateTicket(ticketDTO, flag);
    }

    public ResponseDTO deleteTicket(TicketDTO ticketDTO){
        return ticketRepository.deleteTicket(ticketDTO.getClientId(), ticket.getTicketCode());
    }
}
