package at.fhv.teamh.hauptsach_ticket.frontend.data

import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import java.util.*

class Message {
    val id = SimpleObjectProperty<UUID>()
    val title = SimpleStringProperty()
    val body = SimpleStringProperty()
}