package com.kapture.ticketservice.service;

import java.util.ArrayList;
import java.util.List;

import com.kapture.ticketservice.mapper.TicketMapper;
import com.kapture.ticketservice.repository.TicketRepository;
import com.kapture.ticketservice.entity.Ticket;
import com.kapture.ticketservice.dto.TicketDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketService {

    TicketRepository ticketRepository;
    TicketMapper ticketMapper;

    @Autowired
    public TicketService(TicketRepository ticketRepository, TicketMapper ticketMapper){
        this.ticketRepository = ticketRepository;
        this.ticketMapper = ticketMapper;
    }

    public List<Ticket> searchTickets(TicketDTO ticketDTO, String status ){
        List<Ticket> fetchedTicket = new ArrayList<>();
        if(status.equals("all")){
            fetchedTicket = ticketRepository.findAllTickets();
        }
        if(status.equals("single")){
            fetchedTicket =  List.of(ticketRepository.getTicketByIndex(ticketDTO.getClientId(), ticketDTO.getTicketCode()));
        }
        if(status.equals("multiple")){
            fetchedTicket =  ticketRepository.getTicket(ticketDTO);
        }
        return fetchedTicket;
    }

    public void addTicket(TicketDTO ticketDTO){
        Ticket ticket = ticketMapper.map(ticketDTO);
        ticketRepository.saveTicket(ticket);
    }

    public void updateTicket(TicketDTO ticketDTO, int flag){
        ticketRepository.updateTicket(ticketDTO, flag);
    }

    public void deleteTicket(TicketDTO ticketDTO){
        ticketRepository.deleteTicket(ticketDTO.getClientId(), ticketDTO.getTicketCode());
    }
}
