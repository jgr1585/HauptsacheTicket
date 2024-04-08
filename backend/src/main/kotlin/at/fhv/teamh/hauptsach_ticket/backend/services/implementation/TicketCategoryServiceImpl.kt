package at.fhv.teamh.hauptsach_ticket.backend.services.implementation

import at.fhv.teamh.hauptsach_ticket.backend.application.BeansHandler.injectEJB
import at.fhv.teamh.hauptsach_ticket.backend.application.DtoConverter.toDto
import at.fhv.teamh.hauptsach_ticket.backend.infrastructure.TicketCategoryRepository
import at.fhv.teamh.hauptsach_ticket.backend.services.TicketCategoryService
import at.fhv.teamh.hauptsach_ticket.library.dto.TicketCategoryDTO
import jakarta.ejb.Local
import jakarta.ejb.Singleton
import java.util.*

@Local(TicketCategoryService::class)
@Singleton
open class TicketCategoryServiceImpl : TicketCategoryService {

    private val ticketCategoryRepository: TicketCategoryRepository by injectEJB()

    override fun findTicketsFromEvent(eventId: UUID): List<TicketCategoryDTO> =
        ticketCategoryRepository.getTicketCategoriesForEvent(eventId).map { it.toDto() }

    override fun getAvailableTicketNumbers(ticketCategoryId: UUID): List<Int> =
        ticketCategoryRepository.getTicketNumbers(ticketCategoryId)
            .filter { it.available }
            .map { it.number }
}