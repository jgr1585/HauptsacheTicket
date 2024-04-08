package at.fhv.teamh.hauptsach_ticket.frontend.data

import at.fhv.teamh.hauptsach_ticket.library.dto.RSSObjectDTO
import javafx.beans.property.SimpleStringProperty

class RSSObject(rssobjekt: RSSObjectDTO) {
    val title = SimpleStringProperty(rssobjekt.title)
    val description = SimpleStringProperty(rssobjekt.description)
}