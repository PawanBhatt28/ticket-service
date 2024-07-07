package com.kapture.ticketservice.constants;

public interface Constants {
	String select = "FROM Ticket t";
	String delete = "delete from Ticket t where t.ticketCode = :ticketCode";
	String updateStatus = "UPDATE Ticket t SET t.status = :status WHERE t.ticketCode = :ticketCode AND t.clientId = :clientId";
	String updateTitle = "UPDATE Ticket t SET t.title = :title WHERE t.ticketCode = :ticketCode AND t.clientId = :clientId";
	
	String kafkaTopic = "topic-ticket";
	String listenerGroup = "group-1";
}
