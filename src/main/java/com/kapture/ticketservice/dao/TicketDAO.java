package com.kapture.ticketservice.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;
import org.springframework.stereotype.Repository;

import com.kapture.ticketservice.constants.Queries;
import com.kapture.ticketservice.dto.TicketDTO;
import com.kapture.ticketservice.entity.Ticket;
import com.kapture.ticketservice.repository.TicketRepository;

import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Repository
public class TicketDAO implements TicketRepository {

	private final SessionFactory sessionFactory;

	public TicketDAO(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void saveTicket(Ticket ticket) {
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.persist(ticket);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	public List<Ticket> findAllTickets() {
		Session session = null;
		List<Ticket> tickets = null;
		try {
			session = sessionFactory.openSession();
			tickets = session.createQuery(Queries.select, Ticket.class).list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return tickets;
	}

	public Ticket getTicketByIndex(int clientId, int ticketCode) {
		Session session = null;
		Ticket ticket = null;
		try {
			session = sessionFactory.openSession();
			HibernateCriteriaBuilder hibernateCriteriaBuilder = session.getCriteriaBuilder();
			CriteriaQuery<Ticket> criteriaQuery = hibernateCriteriaBuilder.createQuery(Ticket.class);
			Root<Ticket> root = criteriaQuery.from(Ticket.class);

			Predicate clientIdPredicate = hibernateCriteriaBuilder.equal(root.get("clientId"), clientId);
			Predicate ticketCodePredicate = hibernateCriteriaBuilder.equal(root.get("ticketCode"), ticketCode);

			criteriaQuery.select(root).where(hibernateCriteriaBuilder.and(clientIdPredicate, ticketCodePredicate));
			ticket = session.createQuery(criteriaQuery).getSingleResult();
		} finally {
			session.close();
		}
		return ticket;
	}

	public List<Ticket> getTicket(TicketDTO ticketDTO) {
		Session session = null;
		try {
			session = sessionFactory.openSession();
			StringBuilder hql = new StringBuilder(Queries.select + " WHERE clientId = :clientId");
			Integer clientId = ticketDTO.getClientId();
			String status = ticketDTO.getStatus();
			String title = ticketDTO.getTitle();
			if (status != null) {
				hql.append(" AND t.status = :status");

			} else if (title != null) {
				hql.append(" AND t.title = :title");
			} else {
				return findAllTickets();
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
			}
			return query.setMaxResults(ticketDTO.getLimit()).getResultList();
		} finally {
			session.close();
		}
	}

	public void updateTicket(TicketDTO ticketDTO, int flag) {
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			Query query = null;
			if (flag == 1) {
				String status = ticketDTO.getStatus();
				query = session.createQuery(Queries.updateStatus).setParameter("status", status)
						.setParameter("ticketCode", ticketDTO.getTicketCode())
						.setParameter("clientId", ticketDTO.getClientId());
			} else {
				String title = ticketDTO.getTitle();
				query = session.createQuery(Queries.updateTitle).setParameter("title", title)
						.setParameter("ticketCode", ticketDTO.getTicketCode())
						.setParameter("clientId", ticketDTO.getClientId());
			}

			query.executeUpdate();
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	public void deleteTicket(int ticketCode) {
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			String hql = Queries.delete + " t.ticketCode = :ticketCode";
			session.createQuery(hql).setParameter("ticketCode", ticketCode).executeUpdate();
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	public void deleteTicket(int ticketCode, int clientId) {
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			String hql = Queries.delete + " t.ticketCode = :ticketCode AND t.clientId = :clientId";
			session.createQuery(hql).setParameter("ticketCode", ticketCode).setParameter("clientId", clientId)
					.executeUpdate();
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
}
