package com.kapture.ticketservice.validation;

import org.springframework.beans.factory.annotation.Autowired;
import com.kapture.ticketservice.dto.ResponseDTO;
import com.kapture.ticketservice.dto.TicketDTO;

public class TicketValidator{

    @Autowired
    ResponseDTO responseDto;
    @Autowired
    TicketDTO ticketDto;

    public ResponseDTO fetchTicketValidator(TicketDTO ticketDto){
        Integer clientId = ticketDto.getClientId();
        Integer ticketCode = ticketDto.getTicketCode();
        Integer limit = ticketDto.getClientId();
        String status = ticketDto.getStatus();
        String title = ticketDto.getTitle();

        if( clientId == null){
            responseDto.setStatus("invalid");
        }
        else if( ticketCode == null && limit == null && status == null && title == null){
            responseDto.setStatus("all");
            responseDto.setObject(ticketDto);
        }
        else if( ticketCode != null && limit == null && status == null && title == null){
            responseDto.setMessage("single");
            responseDto.setObject(ticketDto);
        }
        else if(ticketCode == null){
            responseDto.setStatus("multiple");
            responseDto.setObject(ticketDto);
        }else{
            responseDto.setStatus("invalid");
        }
        return responseDto;
    }

    public ResponseDTO addTicketValidator(TicketDTO ticketDTO){
        Integer clientId = ticketDto.getClientId();
        Integer ticketCode = ticketDto.getTicketCode();

        if(clientId != null && ticketCode != null){
            responseDto.setStatus("valid");
            responseDto.setObject(ticketDTO);
        }else{
            responseDto.setStatus("invalid");
        }
        return responseDto;
    }

    public ResponseDTO updateTicketValidator(TicketDTO ticketDTO){
        Integer clientId = ticketDto.getClientId();
        Integer ticketCode = ticketDto.getTicketCode();
        String status = ticketDto.getStatus();
        String title = ticketDto.getTitle();

        if((clientId == null || ticketCode == null) || (status == null && title == null)  ){
            responseDto.setStatus("invalid");
        }else{
            responseDto.setStatus("valid");
            responseDto.setObject(ticketDTO);
        }
        return responseDto;
    }
}

