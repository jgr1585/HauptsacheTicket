package at.fhv.teamh.hauptsach_ticket.backend.services

import at.fhv.teamh.hauptsach_ticket.library.dto.TicketDTO
import jakarta.ejb.Local
import java.util.*

@Local
interface TicketService {
    fun addTicketsToCart(ticketCategoryId: UUID, amount: Int, ticketNumbers: List<Int>): List<TicketDTO>
    fun removeTicketsFromCart(ticketIds: List<UUID>)
}