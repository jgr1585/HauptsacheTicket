package at.fhv.teamh.hauptsach_ticket.backend.rest

import at.fhv.teamh.hauptsach_ticket.backend.application.BeansHandler.injectEJB
import at.fhv.teamh.hauptsach_ticket.backend.application.DtoConverter.toDto
import at.fhv.teamh.hauptsach_ticket.backend.domain.DomainFactory
import at.fhv.teamh.hauptsach_ticket.backend.rest.controller.TicketController
import at.fhv.teamh.hauptsach_ticket.backend.services.TicketCategoryService
import jakarta.ws.rs.NotFoundException
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.`when`
import java.util.*
import kotlin.test.assertEquals

class TicketControllerTest {
    private val ticketCategoryService: TicketCategoryService by injectEJB()
    private val ticketController = TicketController()

    @Test
    fun `GIVEN eventId WHEN getTicketsForEvent THEN returnTicketCategory`() {
        //GIVEN
        val eventId = UUID.randomUUID()
        val ticketCategoryExpected = with (DomainFactory) {
            listOf(createTicketCategory().toDto())
        }

        `when`(ticketCategoryService.findTicketsFromEvent(eventId)).thenReturn(ticketCategoryExpected)

        //WHEN
        val ticketCategoryActual = ticketController.getTicketsForEvent(eventId)

        //THEN
        assertEquals(ticketCategoryExpected, ticketCategoryActual)
    }

    @Test
    fun `GIVEN invalidEvent WHEN getTicketsForEvent THEN throw`() {
        //GIVEN
        val eventId = UUID.randomUUID()

        `when`(ticketCategoryService.findTicketsFromEvent(eventId)).thenThrow(RuntimeException())

        //WHEN

        //THEN
        assertThrows<NotFoundException> {
            ticketController.getTicketsForEvent(eventId)
        }
    }
}