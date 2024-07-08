package com.kapture.ticketservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kapture.ticketservice.dto.ResponseDTO;
import com.kapture.ticketservice.dto.TicketDTO;
import com.kapture.ticketservice.entity.Ticket;
import com.kapture.ticketservice.service.TicketService;
import com.kapture.ticketservice.util.InvalidInputException;
import com.kapture.ticketservice.validation.RequestValidator;

@RestController
public class TicketController {

	@Autowired
	private TicketService ticketService;

	@Autowired
	private RequestValidator requestValidator;

	@PostMapping("ticket")
	public ResponseDTO ResposeDTO(@RequestBody TicketDTO ticketDTO) throws InvalidInputException {
		ResponseDTO responseDTO = requestValidator.postRequestValidator(ticketDTO);
		if (responseDTO.getStatus().equals("Success")) {
			Ticket ticket = ticketService.saveTicket(ticketDTO);
			responseDTO.setObject(ticket);
		}
		return responseDTO;
	}

	@PostMapping("tickets")
	public ResponseDTO saveTickets(@RequestBody List<TicketDTO> tickets) throws InvalidInputException {
		ResponseDTO responseDTO = new ResponseDTO();
		for (TicketDTO ticketDTO : tickets) {
			responseDTO = requestValidator.postRequestValidator(ticketDTO);
			if (responseDTO.getStatus().equals("Failure")) {
				return responseDTO;
			}
		}
		responseDTO.setObject(ticketService.saveTicket(tickets));
		return responseDTO;

	}

	@Cacheable(key = "#clientId + '-' + #ticketCode", value = "Ticket")
	@GetMapping("ticket/{clientId}/{ticketCode}")
	public ResponseDTO getTicket(@PathVariable int clientId, @PathVariable int ticketCode)
			throws InvalidInputException {
		ResponseDTO responseDTO = requestValidator.IndexRequestValidator(clientId, ticketCode);
		if (responseDTO.getStatus().equals("Success")) {
			responseDTO.setObject(ticketService.getTicket(clientId, ticketCode));
		}
		return responseDTO;
	}

	@GetMapping("getRequiredticktes")
	public ResponseDTO getTickets(@RequestBody TicketDTO ticketDTO) throws InvalidInputException {
		ResponseDTO responseDTO = requestValidator.getRequiredValidator(ticketDTO);
		if (responseDTO.getStatus().equals("Success")) {
			responseDTO.setObject(ticketService.getTickets(ticketDTO));
		} 
		return responseDTO;
	}


	@PutMapping("update")
	public ResponseDTO updateTicket(@RequestBody TicketDTO ticketDTO)
			throws InvalidInputException {
		ResponseDTO responseDTO = requestValidator.updateRequestValidator(ticketDTO);
		if (responseDTO.getStatus().equals("Success")) {
			responseDTO.setObject(ticketService.updateTicket(ticketDTO));
		} 
		return responseDTO;
	}


}
