package com.kapture.ticketservice.service;

import com.kapture.ticketservice.mapper.TicketMapper;
import org.springframework.beans.factory.annotation.Autowired;
import com.kapture.ticketservice.repository.TicketRepository;
import com.kapture.ticketservice.dto.ResponseDTO;
import com.kapture.ticketservice.entity.Ticket;
import com.kapture.ticketservice.dto.TicketDTO;
import org.springframework.http.HttpStatus;

public class TicketService {

    @Autowired
    Ticket ticket;
    @Autowired
    TicketRepository ticketRepository;
    @Autowired
    ResponseDTO responseDTO;
    @Autowired
    TicketMapper ticketMapper;
    @Autowired
    TicketDTO ticketDTO;

    public ResponseDTO searchTickets(ResponseDTO responseDTO){
        String status = responseDTO.getStatus();

        if(status.equals("all")){
            ticketRepository.findAllTickets();
        }
        if(status.equals("single")){
            ticketRepository.getTicketByIndex(responseDTO.getObject().getClientId(), responseDTO.getObject().getTicketCode());
        }
        if(status.equals("multiple")){
            ticketRepository.getTicket(responseDTO.getObject());
        }

        responseDTO.setHttpStatus(HttpStatus.OK);
        return responseDTO;
    }

    public ResponseDTO addTicket(ResponseDTO responseDTO){
        ticket = ticketMapper.map(responseDTO.getObject());
        ticketRepository.saveTicket(ticket);

        responseDTO.setHttpStatus(HttpStatus.OK);
        return responseDTO;
    }

    public ResponseDTO updateTicket(ResponseDTO responseDTO){
        ticketDTO = responseDTO.getObject();
        ticketRepository.updateTicket(ticketDTO, 1);

        responseDTO.setHttpStatus(HttpStatus.OK);
        return responseDTO;
    }
}
