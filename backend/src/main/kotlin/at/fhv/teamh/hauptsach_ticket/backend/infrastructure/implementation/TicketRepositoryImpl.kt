package at.fhv.teamh.hauptsach_ticket.backend.infrastructure.implementation

import at.fhv.teamh.hauptsach_ticket.backend.application.DefaultData
import at.fhv.teamh.hauptsach_ticket.backend.application.PersistenceManager
import at.fhv.teamh.hauptsach_ticket.backend.domain.Ticket
import at.fhv.teamh.hauptsach_ticket.backend.domain.TicketCategory
import at.fhv.teamh.hauptsach_ticket.backend.infrastructure.TicketRepository
import jakarta.ejb.Local
import jakarta.ejb.Singleton
import java.util.*

@Local(TicketRepository::class)
@Singleton
open class TicketRepositoryImpl : TicketRepository {
    override fun getAllTickets(maximumAmount: Int): List<Ticket> {
        val em = PersistenceManager.getEntityManagerInstance()
        val ticketQuery = em.createQuery(
            "select t from Ticket t",
            Ticket::class.java
        ).apply {
            maxResults = maximumAmount
        }

        return em.use {
            ticketQuery.resultList.toList()
        }
    }

    override fun getTicketsByCategory(ticketCategoryId: UUID, maximumAmount: Int): List<Ticket> {
        val em = PersistenceManager.getEntityManagerInstance()
        val ticketQuery = em.createQuery(
            "select t from Ticket t where t.ticketCategory.id = :ticketCategoryId",
            Ticket::class.java
        ).apply {
            setParameter("ticketCategoryId", ticketCategoryId)
            maxResults = maximumAmount
        }

        return em.use {
            ticketQuery.resultList.toList()
        }
    }

    override fun createTicketsForCategory(
        ticketCategory: TicketCategory,
        amount: Int,
        ticketNumbers: List<Int>,
        availableTicketNumbers: List<Int>,
    ): List<Ticket> {
        val em = PersistenceManager.getEntityManagerInstance()
        val tickets = mutableListOf<Ticket>()
        val remainingTicketNumbers = availableTicketNumbers.toMutableList()
        remainingTicketNumbers.removeAll(ticketNumbers.toSet())

        tickets.addAll(ticketNumbers.map {
            Ticket(ticketNumber = it, ticketCategory = ticketCategory)
        })

        while (tickets.size < amount) {
            tickets.add(Ticket(ticketNumber = remainingTicketNumbers[0], ticketCategory = ticketCategory))
            remainingTicketNumbers.removeAt(0)
        }

        em.transaction.begin()
        tickets.map { em.merge(it) }
        em.transaction.commit()

        em.close()

        return tickets
    }

    override fun removeTicketsFromRepository(ticketIds: List<UUID>) {

        println(ticketIds.filter { it == DefaultData.order.id })

        PersistenceManager.getEntityManagerInstance().use { em ->
            em.transaction.begin()

            em.createQuery(
                "DELETE Ticket t WHERE t.id IN :ticketIds AND t.order.id = :orderId"
            ).apply {
                setParameter("ticketIds", ticketIds)
                setParameter("orderId", DefaultData.order.id)
            }.executeUpdate()

            em.transaction.commit()
        }
    }

    override fun getTicketById(ticketId: UUID): Ticket {
        val em = PersistenceManager.getEntityManagerInstance()
        val ticketQuery = em.createQuery(
            "select t from Ticket t where t.id = :ticketId",
            Ticket::class.java
        ).apply {
            setParameter("ticketId", ticketId)
        }

        return try {
            ticketQuery.singleResult
        } catch (e: Exception) {
            DefaultData.ticket
        } finally {
            em.close()
        }
    }
}