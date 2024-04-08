package at.fhv.teamh.hauptsach_ticket.backend.rest

import at.fhv.teamh.hauptsach_ticket.backend.application.BeansHandler.injectEJB
import at.fhv.teamh.hauptsach_ticket.backend.application.DtoConverter.toDto
import at.fhv.teamh.hauptsach_ticket.backend.domain.DomainFactory
import at.fhv.teamh.hauptsach_ticket.backend.rest.controller.EventController
import at.fhv.teamh.hauptsach_ticket.backend.services.EventService
import jakarta.ws.rs.WebApplicationException
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.`when`
import kotlin.test.assertEquals

class EventControllerTest {
    private val eventService : EventService by injectEJB()
    private val eventController = EventController()

    @Test
    fun `GIVEN noSearchString WHEN searchEvents THEN returnEvents`() {
        //GIVEN
        val searchTerm = ""
        val eventsExpected = with (DomainFactory) {
            listOf(
                createEvent(),
                createEvent(),
                createEvent(),
            )
        }.map { it.toDto() }

        `when`(eventService.searchEvents(searchTerm)).thenReturn(eventsExpected)

        //WHEN
        val eventsActual = eventController.searchEvents(searchTerm)

        //THEN
        assertEquals(eventsExpected, eventsActual)
    }

    @Test
    fun `GIVEN wrongSearchString WHEN searchEvents THEN throwTicketNotFoundException`() {
        //GIVEN
        val searchTerm = "asfd"

        `when`(eventService.searchEvents(searchTerm)).thenReturn(emptyList())

        //THEN
        assertThrows<WebApplicationException>("Ticket not found") { eventController.searchEvents(searchTerm) }
    }

    @Test
    fun `GIVEN noSearchString WHEN totalSearchEvents THEN returnThreeEvents`() {
        //GIVEN
        val searchTerm = ""
        val eventsExpected = with(DomainFactory) {
            listOf(
                createEvent(),
                createEvent(),
                createEvent(),
            )
        }.map { it.toDto() }

        `when`(eventService.totalSearchEvents(searchTerm)).thenReturn(eventsExpected.size)

        //WHEN
        val eventNumberActual = eventController.totalSearchEvents(searchTerm)

        //THEN
        assertEquals(eventsExpected.size, eventNumberActual)
    }

    @Test
    fun `GIVEN nothing WHEN totalEvents THEN returnTotalEventNumber`() {
        //GIVEN
        val totalEventsExpected = 25

        `when`(eventService.totalEvents()).thenReturn(totalEventsExpected)

        //WHEN
        val totalEventsActual = eventController.totalEvents()

        //THEN
        assertEquals(totalEventsExpected, totalEventsActual)
    }

    @Test
    fun `GIVEN nothing WHEN getEvents THEN returnThreeEvents`() {
        //GIVEN
        val eventsExpected = with (DomainFactory) {
            listOf(
                createEvent(),
                createEvent(),
                createEvent(),
            )
        }.map { it.toDto() }

        `when`(eventService.getEvents()).thenReturn(eventsExpected)

        //WHEN
        val eventsActual = eventController.getEvents(0, 25)

        //THEN
        assertEquals(eventsExpected, eventsActual)
    }
}