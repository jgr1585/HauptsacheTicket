package at.fhv.teamh.hauptsach_ticket.backend.infrastructure

import at.fhv.teamh.hauptsach_ticket.backend.domain.TicketCategory
import at.fhv.teamh.hauptsach_ticket.backend.domain.TicketNumber
import jakarta.ejb.Local
import java.util.*

@Local
interface TicketCategoryRepository {
    fun getTicketCategoriesForEvent(eventId: UUID): List<TicketCategory>
    fun getTicketCategoryById(ticketCategoryId: UUID): TicketCategory
    fun getTicketNumbers(ticketCategoryId: UUID): List<TicketNumber>
    fun markTicketAsUnavailable(ticketCategoryId: UUID, ticketNumber: Int)
    fun makeTicketNumbersAvailable(ticketCategoryId: UUID, ticketNumbers: List<Int>)
}