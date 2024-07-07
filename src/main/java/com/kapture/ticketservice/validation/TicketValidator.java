package com.kapture.ticketservice.validation;

import org.springframework.beans.factory.annotation.Autowired;

import com.kapture.ticketservice.dto.ResponseDTO;
import com.kapture.ticketservice.dto.TicketDTO;

public class TicketValidator{

    @Autowired
    ResponseDTO responseDTO;

    public ResponseDTO fetchTicketValidator(TicketDTO ticketDto){
        Integer clientId = ticketDto.getClientId();
        Integer ticketCode = ticketDto.getTicketCode();
        Integer limit = ticketDto.getClientId();
        String status = ticketDto.getStatus();
        String title = ticketDto.getTitle();

        if( clientId == null){
            responseDTO.setStatus("invalid");
        }
        else if( ticketCode == null && limit == null && status == null && title == null){
            responseDTO.setStatus("all");
            responseDTO.setObject(ticketDto);
        }
        else if( ticketCode != null && limit == null && status == null && title == null){
            responseDTO.setMessage("single");
            responseDTO.setObject(ticketDto);
        }
        else if(ticketCode == null){
            responseDTO.setStatus("multiple");
            responseDTO.setObject(ticketDto);
        }else{
            responseDTO.setStatus("invalid");
        }
        return responseDTO;
    }

    public ResponseDTO addTicketValidator(TicketDTO ticketDTO){
        Integer clientId = ticketDTO.getClientId();
        Integer ticketCode = ticketDTO.getTicketCode();

        if(clientId != null && ticketCode != null){
            responseDTO.setStatus("valid");
            responseDTO.setObject(ticketDTO);
        }else{
            responseDTO.setStatus("invalid");
        }
        return responseDTO;
    }

    public ResponseDTO updateTicketValidator(TicketDTO ticketDTO){
        Integer clientId = ticketDTO.getClientId();
        Integer ticketCode = ticketDTO.getTicketCode();
        String status = ticketDTO.getStatus();
        String title = ticketDTO.getTitle();

        if((clientId == null || ticketCode == null) || (status == null && title == null)  ){
            responseDTO.setStatus("invalid");
        }else{
            responseDTO.setStatus("valid");
            responseDTO.setObject(ticketDTO);
        }
        return responseDTO;
    }

    public ResponseDTO deleteTicketValidator(TicketDTO ticketDTO){
        Integer clientID = ticketDTO.getClientId();
        Integer ticketCode = ticketDTO.getTicketCode();

        if(clientID == null || ticketCode == null){
            responseDTO.setStatus("invalid");
        }else{
            responseDTO.setStatus("valid");
            responseDTO.setObject(ticketDTO);
        }
        return responseDTO;
    }
}

