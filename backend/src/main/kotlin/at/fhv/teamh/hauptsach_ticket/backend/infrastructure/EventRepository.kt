package at.fhv.teamh.hauptsach_ticket.backend.infrastructure

import at.fhv.teamh.hauptsach_ticket.backend.domain.Event
import jakarta.ejb.Local
import java.time.LocalDate
import java.util.*

@Local
interface EventRepository {
    fun getEvents(eventNumber: Int = 0, eventsPerPage: Int = 25): List<Event>

    fun totalEvents(): Int

    fun searchEvents(
        searchString: String,
        eventName: String? = null,
        genre: String? = null,
        date: LocalDate? = null,
        eventNumber: Int = 0,
        eventsPerPage: Int = 25
    )
            : List<Event>

    fun totalSearchEvents(
        searchString: String,
        eventName: String? = null,
        genre: String? = null,
        date: LocalDate? = null
    ): Int

    fun getEventByCategoryId(ticketCategoryId: UUID): Event

    fun findEventName(eventName: String): List<Event>

    fun findEventGenre(genre: String): List<Event>
}