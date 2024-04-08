package at.fhv.teamh.hauptsach_ticket.backend.services.implementation

import at.fhv.teamh.hauptsach_ticket.backend.application.BeansHandler.injectEJB
import at.fhv.teamh.hauptsach_ticket.backend.application.DtoConverter.toDto
import at.fhv.teamh.hauptsach_ticket.backend.domain.Ticket
import at.fhv.teamh.hauptsach_ticket.backend.infrastructure.TicketCategoryRepository
import at.fhv.teamh.hauptsach_ticket.backend.infrastructure.TicketRepository
import at.fhv.teamh.hauptsach_ticket.backend.services.TicketService
import at.fhv.teamh.hauptsach_ticket.library.dto.TicketDTO
import jakarta.ejb.Local
import jakarta.ejb.Singleton
import java.util.*

@Local(TicketService::class)
@Singleton
open class TicketServiceImpl : TicketService {

    private val ticketRepository: TicketRepository by injectEJB()
    private val ticketCategoryRepository: TicketCategoryRepository by injectEJB()

    override fun addTicketsToCart(ticketCategoryId: UUID, amount: Int, ticketNumbers: List<Int>): List<TicketDTO> {
        val ticketCategory = ticketCategoryRepository.getTicketCategoryById(ticketCategoryId)
        val availableTicketNumbers = ticketCategoryRepository.getTicketNumbers(ticketCategoryId)
            .filter { it.available }
            .map { it.number }

        var tickets: List<Ticket> = listOf()

        if (availableTicketNumbers.containsAll(ticketNumbers)) {
            tickets =
                ticketRepository.createTicketsForCategory(
                    ticketCategory,
                    amount,
                    ticketNumbers,
                    availableTicketNumbers
                )
        }

        return tickets.map { it.toDto() }
    }

    override fun removeTicketsFromCart(ticketIds: List<UUID>) {
        ticketRepository.removeTicketsFromRepository(ticketIds)
    }
}