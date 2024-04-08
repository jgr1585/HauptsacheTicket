package at.fhv.teamh.hauptsach_ticket.backend.infrastructure.implementation

import at.fhv.teamh.hauptsach_ticket.backend.application.DefaultData
import at.fhv.teamh.hauptsach_ticket.backend.application.PersistenceManager
import at.fhv.teamh.hauptsach_ticket.backend.domain.Event
import at.fhv.teamh.hauptsach_ticket.backend.infrastructure.EventRepository
import jakarta.ejb.Local
import jakarta.ejb.Singleton
import java.time.LocalDate
import java.util.*

@Local(EventRepository::class)
@Singleton
open class EventRepositoryImpl : EventRepository {
    override fun getEvents(eventNumber: Int, eventsPerPage: Int): List<Event> {
        require(eventNumber >= 0 && eventsPerPage >= 0) {
            "eventNumber and eventsPerPage must be greater than 0"
        }

        val em = PersistenceManager.getEntityManagerInstance()
        val query = em.createQuery(
            "select e from Event e",
            Event::class.java
        )
            .setFirstResult(eventNumber)
            .setMaxResults(eventsPerPage)

        return em.use {
            query.resultList.toList()
        }
    }

    override fun totalEvents(): Int {
        val em = PersistenceManager.getEntityManagerInstance()
        val query = em.createQuery(
            "select count(e.id)  from Event e",
            java.lang.Long::class.java
        )

        return em.use {
            query.singleResult.toInt()
        }
    }

    override fun searchEvents(
        searchString: String,
        eventName: String?,
        genre: String?,
        date: LocalDate?,
        eventNumber: Int,
        eventsPerPage: Int
    ): List<Event> {
        val em = PersistenceManager.getEntityManagerInstance()
        val query = em.createQuery(
            "select e from Event e where ( :searchString = '%%' or ( :searchString <> '%%'" +
                    " and ((lower(e.series.name) like :searchString) or (lower(e.series.artist) like :searchString ))))" +
                    "and ((:eventName <> '%%' and (lower(e.name) like lower(:eventName))) or :eventName = '%%')" +
                    "and ((:genre <> '%%' and (lower(e.genre) like lower(:genre))) or :genre = '%%')" +
                    "and e.date >= :eventDate",
            Event::class.java
        ).apply {
            setParameter("searchString", "%${searchString.lowercase()}%")
            setParameter("eventName", "%${eventName?.lowercase() ?: ""}%")
            setParameter("genre", "%${genre?.lowercase() ?: ""}%")
            setParameter("eventDate", date ?: LocalDate.of(1970, 1, 1))
            firstResult = eventNumber
            maxResults = eventsPerPage
        }

        return em.use {
            query.resultList
        }
    }

    override fun totalSearchEvents(searchString: String, eventName: String?, genre: String?, date: LocalDate?): Int {
        val em = PersistenceManager.getEntityManagerInstance()
        val query = em.createQuery(
            "select count(e.id) from Event e where ( :searchString = '%%' or ( :searchString <> '%%'" +
                    " and ((lower(e.series.name) like :searchString) or (lower(e.series.artist) like :searchString ))))" +
                    "and ((:eventName <> '%%' and (lower(e.name) like lower(:eventName))) or :eventName = '%%')" +
                    "and ((:genre <> '%%' and (lower(e.genre) like lower(:genre))) or :genre = '%%')" +
                    "and e.date >= :eventDate",
            java.lang.Long::class.java
        ).apply {
            setParameter("searchString", "%${searchString.lowercase()}%")
            setParameter("eventName", "%${eventName?.lowercase() ?: ""}%")
            setParameter("genre", "%${genre?.lowercase() ?: ""}%")
            setParameter("eventDate", date ?: LocalDate.of(1970, 1, 1))
        }
        return em.use {
            query.singleResult.toInt()
        }
    }

    override fun getEventByCategoryId(ticketCategoryId: UUID): Event {
        val em = PersistenceManager.getEntityManagerInstance()
        val query = em.createQuery(
            "select c.event from TicketCategory c where c.id = :ticketCategoryId",
            Event::class.java
        ).apply {
            setParameter("ticketCategoryId", ticketCategoryId)
        }

        return try {
            query.singleResult
        } catch (e: Exception) {
            DefaultData.event
        } finally {
            em.close()
        }
    }

    override fun findEventName(eventName: String): List<Event> {
        PersistenceManager.getEntityManagerInstance().use { em ->
            return em.createQuery(
                "select e from Event e where lower(e.name) like lower(:eventName)",
                Event::class.java
            ).apply {
                setParameter("eventName", "%$eventName%")
            }.resultList
        }
    }

    override fun findEventGenre(genre: String): List<Event> {
        PersistenceManager.getEntityManagerInstance().use { em ->
            return em.createQuery(
                "select e from Event e where lower(e.genre) like lower(:genre)",
                Event::class.java
            ).apply {
                setParameter("genre", "%$genre%")
            }.resultList
        }
    }
}