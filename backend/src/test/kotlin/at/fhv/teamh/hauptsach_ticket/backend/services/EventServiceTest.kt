package at.fhv.teamh.hauptsach_ticket.backend.services

import at.fhv.teamh.hauptsach_ticket.backend.application.BeansHandler.injectEJB
import at.fhv.teamh.hauptsach_ticket.backend.application.DtoConverter.toDto
import at.fhv.teamh.hauptsach_ticket.backend.domain.DomainFactory
import at.fhv.teamh.hauptsach_ticket.backend.infrastructure.EventRepository
import at.fhv.teamh.hauptsach_ticket.backend.services.implementation.EventServiceImpl
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import kotlin.test.Test
import kotlin.test.assertEquals

class EventServiceTest {

    private val eventService = EventServiceImpl()
    private val eventRepository by injectEJB<EventRepository>()

    @Test
    fun `GIVEN eventList WHEN getAllEvents THEN returnAllEvents`() {

        //GIVEN
        val eventList = with(DomainFactory) {
            listOf(
                createEvent(),
                createEvent(),
                createEvent()
            )
        }

        `when`(eventRepository.getEvents()).thenReturn(eventList)

        val expected = eventList.map { it.toDto() }
        //WHEN

        val actual = eventService.getEvents()

        //THEN
        assertEquals(expected, actual)
    }

    @Test
    fun `GIVEN eventList WHEN totalEvents THEN returnTotalEvents`() {

        //GIVEN
        val eventList = with(DomainFactory) {
            listOf(
                createEvent(),
                createEvent(),
                createEvent()
            )
        }

        `when`(eventRepository.totalEvents()).thenReturn(eventList.size)

        val expected = eventList.size
        //WHEN

        val actual = eventService.totalEvents()

        //THEN
        assertEquals(expected, actual)
    }

    @Test
    fun `GIVEN searchString WHEN searchForEvent THEN returnEvent`() {
        //GIVEN
        val eventList = with(DomainFactory) {
            listOf(
                createEvent(),
                createEvent(),
                createEvent()
            )
        }

        val series = eventList.first().series.toString()

        `when`(eventRepository.searchEvents(series)).thenReturn(listOf(eventList.first()))

        //WHEN
        val actual = eventService.searchEvents(series)

        //THEN
        verify(eventRepository).searchEvents(series)
        assertEquals(listOf(eventList.first().toDto()), actual)
    }

    @Test
    fun `GIVEN searchString WHEN totalSearchEvents THEN returnTotalEvents`() {
        //GIVEN
        val eventList = with(DomainFactory) {
            listOf(
                createEvent(),
                createEvent(),
                createEvent()
            )
        }

        val series = eventList.first().series.toString()

        `when`(eventRepository.totalSearchEvents(series)).thenReturn(eventList.size)

        //WHEN
        val actual = eventService.totalSearchEvents(series)

        //THEN
        verify(eventRepository).totalSearchEvents(series)
        assertEquals(eventList.size, actual)
    }

    @Test
    fun `GIVEN ticketCategoryId WHEN getEventByCategoryId THEN returnEvent`() {
        //GIVEN
        val ticketCategory = with(DomainFactory) {
            createTicketCategory()
        }

        val event = ticketCategory.event

        `when`(eventRepository.getEventByCategoryId(ticketCategory.id)).thenReturn(event)

        //WHEN
        val actual = eventService.getEventByCategoryId(ticketCategory.id)

        //THEN
        verify(eventRepository).getEventByCategoryId(ticketCategory.id)
        assertEquals(event.toDto(), actual)
    }

    @Test
    fun `GIVEN eventList WHEN findEventName THEN returnEvent`() {
        //GIVEN
        val eventList = with(DomainFactory) {
            listOf(
                createEvent(),
                createEvent(),
                createEvent()
            )
        }

        val eventName = eventList.first().name

        `when`(eventRepository.findEventName(eventName)).thenReturn(listOf(eventList.first()))

        //WHEN
        val actual = eventService.findEventName(eventName)

        //THEN
        verify(eventRepository).findEventName(eventName)
        assertEquals(listOf(eventList.first().toDto()), actual)
    }

    @Test
    fun `GIVEN eventList WHEN findEventGerne THEN returnTotalEvents`() {
        //GIVEN
        val eventList = with(DomainFactory) {
            listOf(
                createEvent(),
                createEvent(),
                createEvent()
            )
        }

        val eventGenre = eventList.first().genre

        `when`(eventRepository.findEventGenre(eventGenre)).thenReturn(listOf(eventList.first()))

        //WHEN
        val actual = eventService.findEventGenre(eventGenre)

        //THEN
        verify(eventRepository).findEventGenre(eventGenre)
        assertEquals(listOf(eventList.first().toDto()), actual)
    }
}