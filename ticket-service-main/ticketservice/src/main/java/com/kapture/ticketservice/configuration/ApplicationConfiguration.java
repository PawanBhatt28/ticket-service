package com.kapture.ticketservice.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.kapture.ticketservice.dto.ResponseDTO;
import com.kapture.ticketservice.util.TicketMapper;
import com.kapture.ticketservice.validation.RequestValidator;

@Configuration
public class ApplicationConfiguration {

	@Bean
	public ResponseDTO createResponse() {
		return new ResponseDTO();
	}
	
	@Bean
	public TicketMapper createMapper() {
		return new TicketMapper();
	}
	
	@Bean
	public RequestValidator createValidator() {
		return new  RequestValidator();
	}
}
