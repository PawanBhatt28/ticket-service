package com.kapture.ticketservice.entity;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="ticket", indexes = {@Index(name="index_clientid_token_code", columnList = "client_id, ticket_code")})
public class Ticket {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name="client_id")
	private int clientId;
	
	@Column(name="ticket_code", unique = true)
	private int ticketCode;
	
	@Column(name="title")
	private String title;
	
	@Column(name="last_modified_date")
	private Timestamp lastModifiedDate;
	
	@Column(name="status")
	private String status;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getClientId() {
		return clientId;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

	public int getTicketCode() {
		return ticketCode;
	}

	public void setTicketCode(int ticket_code) {
		this.ticketCode = ticket_code;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Timestamp getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Timestamp lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Ticket [id=" + id + ", clientId=" + clientId + ", ticket_code=" + ticketCode + ", title=" + title
				+ ", lastModifiedDate=" + lastModifiedDate + ", status=" + status + "]";
	}
	
}
