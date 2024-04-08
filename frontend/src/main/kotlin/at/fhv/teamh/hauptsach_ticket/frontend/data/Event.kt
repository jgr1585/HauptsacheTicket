package at.fhv.teamh.hauptsach_ticket.frontend.data

import at.fhv.teamh.hauptsach_ticket.library.dto.EventDTO
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty

class Event(eventDTO: EventDTO) {
    val id = SimpleObjectProperty(eventDTO.id)
    val series = SimpleStringProperty(eventDTO.series.name)
    val artist = SimpleStringProperty(eventDTO.series.artist)
    val name = SimpleStringProperty(eventDTO.name)
    val location = SimpleStringProperty(eventDTO.location.address)
    val gerne = SimpleStringProperty(eventDTO.genre)
    val date = SimpleStringProperty(eventDTO.date.toString())
}