package at.fhv.teamh.hauptsach_ticket.backend.infrastructure

import at.fhv.teamh.hauptsach_ticket.backend.domain.Ticket
import at.fhv.teamh.hauptsach_ticket.backend.domain.TicketCategory
import jakarta.ejb.Local
import java.util.*

@Local
interface TicketRepository {
    fun getAllTickets(maximumAmount: Int = 500): List<Ticket>
    fun getTicketsByCategory(ticketCategoryId: UUID, maximumAmount: Int = 500): List<Ticket>
    fun createTicketsForCategory(
        ticketCategory: TicketCategory,
        amount: Int,
        ticketNumbers: List<Int>,
        availableTicketNumbers: List<Int>
    ): List<Ticket>

    fun removeTicketsFromRepository(ticketIds: List<UUID>)
    fun getTicketById(ticketId: UUID): Ticket
}