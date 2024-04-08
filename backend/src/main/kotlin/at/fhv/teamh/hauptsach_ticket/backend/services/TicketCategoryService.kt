package at.fhv.teamh.hauptsach_ticket.backend.services

import at.fhv.teamh.hauptsach_ticket.library.dto.TicketCategoryDTO
import jakarta.ejb.Local
import java.util.*

@Local
interface TicketCategoryService {
    fun findTicketsFromEvent(eventId: UUID): List<TicketCategoryDTO>
    fun getAvailableTicketNumbers(ticketCategoryId: UUID): List<Int>
}