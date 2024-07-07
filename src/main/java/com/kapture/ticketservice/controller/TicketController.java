package com.kapture.ticketservice.controller;

import com.kapture.ticketservice.entity.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.kapture.ticketservice.dto.ResponseDTO;
import com.kapture.ticketservice.service.TicketService;
import com.kapture.ticketservice.validation.TicketValidator;
import com.kapture.ticketservice.dto.TicketDTO;


public class TicketController{

    @Autowired
    TicketService ticketService;
    @Autowired
    TicketValidator ticketValidator;
    @Autowired
    ResponseDTO responseDTO;

    @GetMapping("/searchTicket")
    public ResponseDTO searchTicket(@RequestBody TicketDTO ticketDTO) {
        ResponseDTO validationResponse = ticketValidator.fetchTicketValidator(ticketDTO);
        ResponseDTO clientResponse = new ResponseDTO();

        if(validationResponse.getStatus().equals("invalid")){
            clientResponse.setHttpStatus(HttpStatus.BAD_REQUEST);
        }else{
            clientResponse.setObject(ticketService.searchTickets((TicketDTO)validationResponse.getObject(), responseDTO.getStatus()));
            clientResponse.setHttpStatus(HttpStatus.OK);
        }

        return clientResponse;
    }

    @PostMapping("/addTicket")
    public ResponseDTO addTicket(@RequestBody TicketDTO ticketDTO){
        ResponseDTO validationResponse = ticketValidator.addTicketValidator(ticketDTO);
        ResponseDTO clientResponse = new ResponseDTO();

        if(validationResponse.getStatus().equals("invalid")){
            clientResponse.setHttpStatus(HttpStatus.BAD_REQUEST);
        }else{
            clientResponse.setObject(ticketService.addTicket((TicketDTO)validationResponse.getObject()));
            clientResponse.setHttpStatus(HttpStatus.OK);
        }

        return clientResponse;
    }

    @PostMapping("/update")
    public ResponseDTO updateTicket(@RequestBody TicketDTO ticketDTO){
        ResponseDTO validationResponse = ticketValidator.updateTicketValidator(ticketDTO);
        ResponseDTO clientResponse = new ResponseDTO();

        if(responseDTO.getStatus().equals("invalid")){
            responseDTO.setHttpStatus(HttpStatus.BAD_REQUEST);
        }else{
            clientResponse.setObject(ticketService.updateTicket((TicketDTO)validationResponse.getObject(), 1));
            clientResponse.setHttpStatus(HttpStatus.OK);
        }

        return responseDTO;
    }

    @DeleteMapping("/delete")
    public ResponseDTO deleteTicket(@RequestBody TicketDTO ticketDTO){
        ResponseDTO validationResponse = ticketValidator.deleteTicketValidator(ticketDTO);
        ResponseDTO clientResponse = new ResponseDTO();

        if(responseDTO.getStatus().equals("invalid")){
            responseDTO.setHttpStatus(HttpStatus.BAD_REQUEST);
        }else{
            clientResponse.setObject(ticketService.deleteTicket((TicketDTO)validationResponse.getObject()));
            clientResponse.setHttpStatus(HttpStatus.OK);
        }

        return clientResponse;
    }
}

