package at.fhv.teamh.hauptsach_ticket.backend.services

import at.fhv.teamh.hauptsach_ticket.backend.application.BeansHandler.injectEJB
import at.fhv.teamh.hauptsach_ticket.backend.application.DtoConverter.toDto
import at.fhv.teamh.hauptsach_ticket.backend.domain.DomainFactory
import at.fhv.teamh.hauptsach_ticket.backend.domain.TicketNumber
import at.fhv.teamh.hauptsach_ticket.backend.infrastructure.TicketCategoryRepository
import at.fhv.teamh.hauptsach_ticket.backend.services.implementation.TicketCategoryServiceImpl
import org.mockito.Mockito.`when`
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals

class TicketCategoryServiceTest {

        private val ticketCategoryService = TicketCategoryServiceImpl()

    private val ticketCategoryRepository: TicketCategoryRepository by injectEJB()

    @Test
    fun `GIVEN ticketList WHEN findTicketsFromEvent THEN returnAllTickets`() {

        //GIVEN
        val id = UUID.randomUUID()

        val ticketList = with(DomainFactory) {
            listOf(
                createTicketCategory(),
                createTicketCategory(),
                createTicketCategory()
            )
        }
        `when`(ticketCategoryRepository.getTicketCategoriesForEvent(id)).thenReturn(ticketList)
        val expected = ticketList.map { it.toDto() }

        //WHEN
        val actual = ticketCategoryService.findTicketsFromEvent(id)

        //THEN
        assertEquals(expected, actual)
    }

    @Test
    fun `GIVEN ticketCategory WHEN getAvailableTicketNumbers THEN returnAvailableTicketNumbers`() {
        //GIVEN
        val ticketCategory = DomainFactory.createTicketCategory()

        val ticketNumbers = listOf(
            TicketNumber(number = 1, ticketCategory = ticketCategory),
            TicketNumber(number = 2, ticketCategory = ticketCategory),
            TicketNumber(number = 3, ticketCategory = ticketCategory, available = false)
        )
        `when`(ticketCategoryRepository.getTicketNumbers(ticketCategory.id)).thenReturn(ticketNumbers)
        val ticketNumbersExpected = ticketNumbers.filter { it.available }.map { it.number }

        //WHEN
        val ticketNumbersActual = ticketCategoryService.getAvailableTicketNumbers(ticketCategory.id)

        //THEN
        assertEquals(ticketNumbersExpected, ticketNumbersActual)
    }
}