package com.kapture.ticketservice.dao;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.kapture.ticketservice.constants.Constants;
import com.kapture.ticketservice.dto.TicketDTO;
import com.kapture.ticketservice.entity.Ticket;
import com.kapture.ticketservice.util.TicketMapper;

import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Repository
public class TicketDAO implements TicketRepository, Constants {

	private final Logger logger = LoggerFactory.getLogger(TicketDTO.class);

	private SessionFactory sessionFactory;
	private RedisTemplate<String, Ticket> redisTemplate;
	private TicketMapper ticketMapper;

	public TicketDAO(SessionFactory sessionFactory, RedisTemplate<String, Ticket> redisTemplate,
			TicketMapper ticketMapper) {
		this.sessionFactory = sessionFactory;
		this.redisTemplate = redisTemplate;
		this.ticketMapper = ticketMapper;
	}

	public Ticket saveTicket(Ticket ticket) {
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			ticket.setLastModifiedDate(new Timestamp(System.currentTimeMillis()));
			session.persist(ticket);
			transaction.commit();
			redisTemplate.opsForHash().put("Ticket", ticket.getClientId() + "->" + ticket.getTicket_code(), ticket);

		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			logger.info("Error in saving the ticket!!!", e);
		} finally {
			session.close();
		}
		return ticket;
	}

	public Object getTicketByIndex(int clientId, int ticketCode) {
		Session session = null;
		Object ticket = null;
		try {
			ticket = redisTemplate.opsForHash().get("Ticket", clientId + "->" + ticketCode);
			if (ticket != null) {
				return ticket;
			}
			session = sessionFactory.openSession();
			HibernateCriteriaBuilder hibernateCriteriaBuilder = session.getCriteriaBuilder();
			CriteriaQuery<Ticket> criteriaQuery = hibernateCriteriaBuilder.createQuery(Ticket.class);
			Root<Ticket> root = criteriaQuery.from(Ticket.class);
			Predicate clientIdPredicate = hibernateCriteriaBuilder.equal(root.get("clientId"), clientId);
			Predicate ticketCodePredicate = hibernateCriteriaBuilder.equal(root.get("ticketCode"), ticketCode);
			criteriaQuery.select(root).where(hibernateCriteriaBuilder.and(clientIdPredicate, ticketCodePredicate));
			try {
				ticket = session.createQuery(criteriaQuery).getSingleResult();
			} catch (Exception e) {
				logger.info("No ticket found");
			}
		} catch (Exception e) {
			logger.info("Error in getting the ticket by id ", e);
		} finally {
			if (session != null)
				session.close();
		}
		return ticket;
	}

	@SuppressWarnings("unchecked")
	public List<Ticket> getTicket(TicketDTO ticketDTO) {
		Session session = null;
		try {
			session = sessionFactory.openSession();
			Integer clientId = ticketDTO.getClientId();
			String status = ticketDTO.getStatus();
			String title = ticketDTO.getTitle();
			Date startDate = ticketDTO.getStartDate();
			Date endDate = ticketDTO.getEndDate();
			Timestamp startTimeStamp = null;
			Timestamp endTimeStamp = null;
			int limit = ticketDTO.getLimit();
			int page = ticketDTO.getPage();
			if (limit == 0) {
				limit = 10;
			}
			if (page == 0) {
				page = 1;
			} else if (page != 0) {
				page = ((page - 1) * 10) + 1;
				limit += page;
			}
			StringBuilder hql = new StringBuilder(Constants.select + " WHERE clientId = :clientId");
			if (startDate != null)
				startTimeStamp = new Timestamp(startDate.getTime());
			if (endDate != null)
				endTimeStamp = new Timestamp(endDate.getTime());
			if (status != null) {
				hql.append(" AND t.status = :status");

			} else if (title != null) {
				hql.append(" AND t.title = :title");
			} else if (startTimeStamp != null && endTimeStamp != null) {
				hql.append(" AND t.lastModifiedDate BETWEEN :startTimeStamp AND :endTimeStamp");
			} else if (startTimeStamp != null) {
				hql.append("AND t.lastModifiedDate >= :startTimeStamp");
			}
			Query query = session.createQuery(hql.toString(), Ticket.class);

			if (clientId != null) {
				query.setParameter("clientId", clientId);
			}

			if (status != null) {
				query.setParameter("status", status);
			}

			if (title != null) {
				query.setParameter("title", title);
			} else if (startTimeStamp != null && endTimeStamp != null) {
				query.setParameter("startTimeStamp", startTimeStamp);
				query.setParameter("endTimeStamp", endTimeStamp);
			} else if (startTimeStamp != null) {
				query.setParameter("startTimeStamp", startTimeStamp);
			}
			List<Ticket> tickets = query.setFirstResult(page).setMaxResults(limit).getResultList();
			tickets.stream().forEach(ticket -> {
				redisTemplate.opsForHash().put("Ticket", clientId + "->" + ticket.getTicket_code(), ticket);
			});

			return tickets;
		} catch (Exception e) {
			logger.info("Error in fetching the tickets!!!", e);
			return null;
		} finally {
			session.close();
		}
	}

	@SuppressWarnings("deprecation")
	public Ticket updateTicket(TicketDTO ticketDTO) {
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			Query query = null;
			String status = ticketDTO.getStatus();
			String title = ticketDTO.getTitle();
			Timestamp lastModifiedDate = new Timestamp(System.currentTimeMillis());
			query = session.createQuery(update).setParameter("status", status).setParameter("title", title)
					.setParameter("ticketCode", ticketDTO.getTicketCode())
					.setParameter("clientId", ticketDTO.getClientId())
					.setParameter("lastModifiedDate", lastModifiedDate);
			query.executeUpdate();
			transaction.commit();
			return ticketMapper.map(ticketDTO);
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			logger.info("Error in updating the ticket!!!", e);
			return null;
		} finally {
			session.close();
		}
	}

}
