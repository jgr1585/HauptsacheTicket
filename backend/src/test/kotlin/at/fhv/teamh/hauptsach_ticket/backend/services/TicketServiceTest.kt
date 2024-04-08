package at.fhv.teamh.hauptsach_ticket.backend.services

import at.fhv.teamh.hauptsach_ticket.backend.application.BeansHandler.injectEJB
import at.fhv.teamh.hauptsach_ticket.backend.application.DtoConverter.toDto
import at.fhv.teamh.hauptsach_ticket.backend.domain.DomainFactory
import at.fhv.teamh.hauptsach_ticket.backend.domain.TicketNumber
import at.fhv.teamh.hauptsach_ticket.backend.infrastructure.TicketCategoryRepository
import at.fhv.teamh.hauptsach_ticket.backend.infrastructure.TicketRepository
import at.fhv.teamh.hauptsach_ticket.backend.services.implementation.TicketServiceImpl
import org.junit.jupiter.api.Test
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import kotlin.test.assertEquals

class TicketServiceTest {

    private val ticketService = TicketServiceImpl()

    private val ticketRepository: TicketRepository by injectEJB()
    private val ticketCategoryRepository: TicketCategoryRepository by injectEJB()

    @Test
    fun `GIVEN ticketIds AND ticketCategory WHEN addTicketsToCart THEN returnTicketDTOs`() {
        //GIVEN
        val tickets = with(DomainFactory) {
            listOf(
                createTicket(),
                createTicket(),
                createTicket(),
            )
        }

        val ticketCategory = tickets[0].ticketCategory
        val ticketNumbers = listOf(
            TicketNumber(number = 1, ticketCategory = ticketCategory),
            TicketNumber(number = 2, ticketCategory = ticketCategory),
            TicketNumber(number = 3, ticketCategory = ticketCategory),
        )

        `when`(ticketCategoryRepository.getTicketCategoryById(ticketCategory.id)).thenReturn(ticketCategory)
        `when`(ticketCategoryRepository.getTicketNumbers(ticketCategory.id)).thenReturn(ticketNumbers)
        `when`(
            ticketRepository.createTicketsForCategory(
                ticketCategory = ticketCategory,
                amount = 3,
                availableTicketNumbers = ticketNumbers.map { it.number },
                ticketNumbers = ticketNumbers.map { it.number },
            )
        ).thenReturn(tickets)

        val ticketsExpected = tickets.map { it.toDto() }

        //WHEN
        val ticketsActual = ticketService.addTicketsToCart(ticketCategory.id, 3, ticketNumbers.map { it.number })

        //THEN
        assertEquals(ticketsExpected, ticketsActual)
    }

    @Test
    fun `GIVEN tickets WHEN removeTicketsFromCart THEN execute removeTicketsFromRepository`() {
        //GIVEN
        val tickets = with(DomainFactory) {
            listOf(
                createTicket(),
                createTicket(),
                createTicket(),
            )
        }

        //WHEN
        ticketService.removeTicketsFromCart(tickets.map { it.id })

        //THEN
        verify(ticketRepository).removeTicketsFromRepository(tickets.map { it.id })
    }
}