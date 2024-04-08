package at.fhv.teamh.hauptsach_ticket.backend.services.implementation

import at.fhv.teamh.hauptsach_ticket.backend.application.BeansHandler.injectEJB
import at.fhv.teamh.hauptsach_ticket.backend.application.DtoConverter.toDto
import at.fhv.teamh.hauptsach_ticket.backend.infrastructure.EventRepository
import at.fhv.teamh.hauptsach_ticket.backend.services.EventService
import at.fhv.teamh.hauptsach_ticket.library.dto.EventDTO
import jakarta.ejb.Local
import jakarta.ejb.Singleton
import java.time.LocalDate
import java.util.*

@Local(EventService::class)
@Singleton
open class EventServiceImpl : EventService {

    private val eventRepository: EventRepository by injectEJB()

    override fun getEvents(eventNumber: Int, eventsPerPage: Int) =
        eventRepository.getEvents(eventNumber, eventsPerPage).map { it.toDto() }

    override fun totalEvents() = eventRepository.totalEvents()

    override fun searchEvents(
        searchString: String,
        eventName: String?,
        genre: String?,
        date: LocalDate?,
        eventNumber: Int,
        eventsPerPage: Int,
    ) =
        eventRepository.searchEvents(searchString, eventName, genre, date, eventNumber, eventsPerPage)
            .map { it.toDto() }

    override fun totalSearchEvents(
        searchString: String,
        eventName: String?,
        genre: String?,
        date: LocalDate?,
    ) =
        eventRepository.totalSearchEvents(searchString, eventName, genre, date)

    override fun getEventByCategoryId(ticketCategoryId: UUID): EventDTO =
        eventRepository.getEventByCategoryId(ticketCategoryId).toDto()

    override fun findEventName(eventName: String): List<EventDTO> =
        eventRepository.findEventName(eventName).map { it.toDto() }

    override fun findEventGenre(genre: String): List<EventDTO> =
        eventRepository.findEventGenre(genre).map { it.toDto() }
}