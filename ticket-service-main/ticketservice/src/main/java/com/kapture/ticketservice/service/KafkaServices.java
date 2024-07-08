package com.kapture.ticketservice.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import com.kapture.ticketservice.constants.Constants;
import com.kapture.ticketservice.dao.TicketRepository;
import com.kapture.ticketservice.entity.Ticket;

@Service
public class KafkaServices implements Constants {

	private KafkaTemplate<String, Object> kafkaTemplate;
	private TicketRepository ticketRepository;

	private final Logger logger = LoggerFactory.getLogger(KafkaListener.class);

	public KafkaServices(KafkaTemplate<String, Object> kafkaTemplate, TicketRepository ticketRepository) {
		this.kafkaTemplate = kafkaTemplate;
		this.ticketRepository = ticketRepository;
	}

	public List<Ticket> produceTicket(List<Ticket> tickets) {
		return tickets.stream().peek(ticket -> {
			CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send(kafkaTopic, ticket);
			future.whenComplete((result, exception) -> {
				if (exception != null) {
					logger.info("Error in pushing the ticket in the Kafka Server ", exception);
				} else {
					logger.info("Successfully pushed ticket in the Kafka Server");
				}
			});
		}).collect(Collectors.toList());
	}

	@KafkaListener(topics = kafkaTopic, groupId = listenerGroup)
	public void consumeTicket(Ticket ticket) {
		try {
			ticketRepository.saveTicket(ticket);
		} catch (Exception e) {
			logger.info("Error in Kafka Listener ", e);
			return;
		} finally {
			logger.info("Listener Consumed");
		}
	}

}
