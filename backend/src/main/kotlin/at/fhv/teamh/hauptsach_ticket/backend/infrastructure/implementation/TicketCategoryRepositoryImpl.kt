package at.fhv.teamh.hauptsach_ticket.backend.infrastructure.implementation

import at.fhv.teamh.hauptsach_ticket.backend.application.DefaultData
import at.fhv.teamh.hauptsach_ticket.backend.application.PersistenceManager
import at.fhv.teamh.hauptsach_ticket.backend.domain.TicketCategory
import at.fhv.teamh.hauptsach_ticket.backend.domain.TicketNumber
import at.fhv.teamh.hauptsach_ticket.backend.infrastructure.TicketCategoryRepository
import jakarta.ejb.Local
import jakarta.ejb.Singleton
import java.util.*

@Local(TicketCategoryRepository::class)
@Singleton
open class TicketCategoryRepositoryImpl : TicketCategoryRepository {
    override fun getTicketCategoriesForEvent(eventId: UUID): List<TicketCategory> {
        val em = PersistenceManager.getEntityManagerInstance()
        val query = em.createQuery(
            "select t from TicketCategory t where t.event.id = :eventId",
            TicketCategory::class.java
        ).apply {
            setParameter("eventId", eventId)
        }


        return em.use {
            query.resultList.toList()
        }

    }

    override fun getTicketCategoryById(ticketCategoryId: UUID): TicketCategory {
        val em = PersistenceManager.getEntityManagerInstance()
        val query = em.createQuery(
            "select t from TicketCategory t where t.id = :ticketCategoryId",
            TicketCategory::class.java
        ).apply {
            setParameter("ticketCategoryId", ticketCategoryId)
        }

        return try {
            query.singleResult
        } catch (e: Exception) {
            DefaultData.ticketCategory
        } finally {
            em.close()
        }
    }

    override fun getTicketNumbers(ticketCategoryId: UUID): List<TicketNumber> {
        val em = PersistenceManager.getEntityManagerInstance()
        val query = em.createQuery(
            "select tn from TicketNumber tn where tn.ticketCategory.id = :ticketCategory",
            TicketNumber::class.java
        ).apply {
            setParameter("ticketCategory", ticketCategoryId)
        }

        return em.use {
            query.resultList.toList()
        }
    }

    override fun markTicketAsUnavailable(ticketCategoryId: UUID, ticketNumber: Int) {
        val em = PersistenceManager.getEntityManagerInstance()
        val query = em.createQuery(
            "select tn from TicketNumber tn " +
                    "where tn.ticketCategory.id = :ticketCategoryId " +
                    "AND tn.number = :ticketNumber",
            TicketNumber::class.java
        ).apply {
            setParameter("ticketCategoryId", ticketCategoryId)
            setParameter("ticketNumber", ticketNumber)
        }

        val ticketNum = query.singleResult
        ticketNum.available = false

        val category = getTicketCategoryById(ticketCategoryId)
        category.remainingTickets--

        em.transaction.begin()
        em.merge(ticketNum)
        em.merge(category)
        em.transaction.commit()
        em.close()
    }

    override fun makeTicketNumbersAvailable(ticketCategoryId: UUID, ticketNumbers: List<Int>) {
        val em = PersistenceManager.getEntityManagerInstance()

        val query = em.createQuery(
            "select tn from TicketNumber tn " +
                    "where tn.ticketCategory.id = :ticketCategoryId " +
                    "AND tn.number in :ticketNumbers " +
                    "AND tn.available = false",
            TicketNumber::class.java
        ).apply {
            setParameter("ticketCategoryId", ticketCategoryId)
            setParameter("ticketNumbers", ticketNumbers)
        }

        val ticketNum = query.resultList.toList()
        ticketNum.forEach { it.available = true }

        val ticketCategory = getTicketCategoryById(ticketCategoryId)
        ticketCategory.remainingTickets = getTicketNumbers(ticketCategoryId).filter { it.available }.size

        em.transaction.begin()
        ticketNum.forEach { em.merge(it) }
        em.merge(ticketCategory)
        em.transaction.commit()
        em.close()
    }
}