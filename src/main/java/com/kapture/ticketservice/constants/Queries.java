package com.kapture.ticketservice.constants;

public interface Queries {
	String select = "FROM TICKET t";
	String delete = "delete from Ticket t where";
	String updateStatus = "UPDATE Ticket t SET t.status = :status WHERE t.ticketCode = :ticketCode AND t.clientId = :clientId";
	String updateTitle = "UPDATE Ticket t SET t.title = :title WHERE t.ticketCode = :ticketCode AND t.clientId = :clientId";
}
