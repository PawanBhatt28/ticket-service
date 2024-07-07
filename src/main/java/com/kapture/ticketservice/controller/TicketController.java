package com.kapture.ticketservice.controller;

import com.kapture.ticketservice.dto.TicketDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.kapture.ticketservice.dto.ResponseDTO;
import com.kapture.ticketservice.service.TicketService;
import com.kapture.ticketservice.validation.TicketValidator;

public class TicketController{

    @Autowired
    TicketService ticketService;
    @Autowired
    TicketValidator ticketValidator;
    @Autowired
    ResponseDTO responseDTO;

    @GetMapping("/searchTicket")
    public ResponseDTO searchTicket(@RequestBody TicketDTO ticketDTO) {
        responseDTO = ticketValidator.fetchTicketValidator(ticketDTO);
        if(responseDTO.getStatus().equals("invalid")){
            responseDTO.setHttpStatus(HttpStatus.BAD_REQUEST);
        }else{
            ticketService.searchTickets(responseDTO);
        }
        return responseDTO;
    }

    @GetMapping("/addTicket")
    public ResponseDTO addTicket(@RequestBody TicketDTO ticketDTO){
        ticketValidator.addTicketValidator(ticketDTO);
        if(responseDTO.getStatus().equals("invalid")){
            responseDTO.setHttpStatus(HttpStatus.BAD_REQUEST);
        }else{
            ticketService.searchTickets(responseDTO);
        }
    }

    @GetMapping("/updateTicket")
    public ResponseDTO updateTicket(@RequestBody TicketDTO ticketDTO){
        ticketValidator.updateTicketValidator(ticketDTO);
        if(responseDTO.getStatus().equals("invalid")){
            responseDTO.setHttpStatus(HttpStatus.BAD_REQUEST);
        }else{
            ticketService.searchTickets(responseDTO);
        }
    }
}

