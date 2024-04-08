package at.fhv.teamh.hauptsach_ticket.frontend.controller

import at.fhv.teamh.hauptsach_ticket.frontend.communication.RemoteFacade
import at.fhv.teamh.hauptsach_ticket.frontend.data.Event
import at.fhv.teamh.hauptsach_ticket.library.dto.EventDTO
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.Controller
import tornadofx.observableListOf
import java.time.LocalDate

class SearchTableDataController : Controller() {
    var itemsPerPage = 25
        private set

    var currentPage = SimpleIntegerProperty(0)

    val data = observableListOf(
        RemoteFacade.getEvents(eventsPerPage = itemsPerPage).map { Event(it) }
    )
    val lastSearchString = SimpleStringProperty("")
    val eventName = SimpleStringProperty(null)
    val eventGenre = SimpleStringProperty(null)
    val eventDate = SimpleObjectProperty<LocalDate>(null)

    fun updateTable(events: List<EventDTO>, searchText: String, eventName: String?, genre: String?, date: LocalDate?) {
        currentPage.value = 0
        data.setAll(events.map { Event(it) })
        lastSearchString.value = searchText
        this.eventName.value = eventName
        this.eventGenre.value = genre
        this.eventDate.value = date
    }
}