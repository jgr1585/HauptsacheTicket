package at.fhv.teamh.hauptsach_ticket.backend.services

import at.fhv.teamh.hauptsach_ticket.library.dto.EventDTO
import jakarta.ejb.Local
import java.time.LocalDate
import java.util.*

@Local
interface EventService {
    fun getEvents(eventNumber: Int = 0, eventsPerPage: Int = 25): List<EventDTO>
    fun totalEvents(): Int
    fun searchEvents(
        searchString: String,
        eventName: String? = null,
        genre: String? = null,
        date: LocalDate? = null,
        eventNumber: Int = 0,
        eventsPerPage: Int = 25,
    ): List<EventDTO>

    fun totalSearchEvents(
        searchString: String,
        eventName: String? = null,
        genre: String? = null,
        date: LocalDate? = null,
    ): Int

    fun getEventByCategoryId(ticketCategoryId: UUID): EventDTO
    fun findEventName(eventName: String): List<EventDTO>
    fun findEventGenre(genre: String): List<EventDTO>
}