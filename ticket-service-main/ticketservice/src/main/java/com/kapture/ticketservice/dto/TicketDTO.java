package com.kapture.ticketservice.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class TicketDTO implements Serializable  {
	private static final long serialVersionUID = 1L;
	Integer clientId;
	Integer ticketCode;
	String title;
	String status;
	Timestamp timestamp;
	int limit;
	public TicketDTO() {
	}
	public TicketDTO(int clientId, int ticketCode, String title, String status, int limit, Timestamp timestamp) {
		this.clientId = clientId;
		this.ticketCode = ticketCode;
		this.title = title;
		this.status = status;
		this.limit = limit;
		this.timestamp = timestamp;
	}
	public Integer getClientId() {
		return clientId;
	}
	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}
	public Integer getTicketCode() {
		return ticketCode;
	}
	public void setTicketCode(Integer ticketCode) {
		this.ticketCode = ticketCode;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Timestamp getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
}
