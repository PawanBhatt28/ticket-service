package com.kapture.ticketservice.validation;

import org.springframework.beans.factory.annotation.Autowired;

import com.kapture.ticketservice.dto.ResponseDTO;
import com.kapture.ticketservice.dto.TicketDTO;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class TicketValidator{

    public ResponseDTO fetchTicketValidator(TicketDTO ticketDto){
        ResponseDTO responseDTO = new ResponseDTO();

        Integer clientId = ticketDto.getClientId();
        Integer ticketCode = ticketDto.getTicketCode();
        Integer limit = ticketDto.getClientId();
        String status = ticketDto.getStatus();
        String title = ticketDto.getTitle();

        if( clientId == null){
            System.out.println("client id null");
            responseDTO.setStatus("invalid");
        }
        else if( ticketCode == null && limit == null && status == null && title == null){
            System.out.println("good place");
            responseDTO.setStatus("all");
            responseDTO.setObject(ticketDto);
        }
        else if( ticketCode != null && limit == null && status == null && title == null){
            System.out.println("only ticket id null");
            responseDTO.setMessage("single");
            responseDTO.setObject(ticketDto);
        }
        else if(ticketCode == null){
            System.out.println("ticket code null");
            responseDTO.setStatus("multiple");
            responseDTO.setObject(ticketDto);
        }else{
            System.out.println("else");
            responseDTO.setStatus("invalid");
        }
        return responseDTO;
    }

    public ResponseDTO addTicketValidator(TicketDTO ticketDTO){
        ResponseDTO responseDTO = new ResponseDTO();

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
        ResponseDTO responseDTO = new ResponseDTO();

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
        ResponseDTO responseDTO = new ResponseDTO();

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

