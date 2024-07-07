package com.kapture.ticketservice.service;
import org.springframework.beans.factory.annotation.Autowired;


import com.kapture.ticketservice.dto.TicketDTO;
import kapture.project.ticketing_sytem.dto.ResponseDto;
import kapture.project.ticketing_sytem.entity.Ticket;
import kapture.project.ticketing_sytem.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class TicketService {

    @Autowired
    Ticket ticket;
    @Autowired
    TicketRepository ticketRepository;
    @Autowired
    ResponseDto responseDto;

    public ResponseDto searchTickets(ResponseDto responseDto){
        String status = responseDto.getStatus();

        if(status.equals("invalid")){

        }
        if(status.equals("all")){
            ticketRepository.findAll();
        }
        if(status.equals("single")){
            ticketRepository.getTicketByIndex(responseDto.getObject().getClientId(), responseDto.getObject().getTicketCode());
        }
        if(status.equals("multiple")){
            ticketRepository.getTickets(responseDto.getObject());
        }

    }
}
