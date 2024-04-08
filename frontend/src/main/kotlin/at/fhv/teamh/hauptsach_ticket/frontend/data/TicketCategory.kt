package at.fhv.teamh.hauptsach_ticket.frontend.data

import at.fhv.teamh.hauptsach_ticket.library.dto.TicketCategoryDTO
import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty

class TicketCategory(ticketCategory: TicketCategoryDTO) {
    val id = SimpleObjectProperty(ticketCategory.id)
    val name = SimpleStringProperty(ticketCategory.name)
    val price = SimpleDoubleProperty(ticketCategory.price)
    val totalTickets = SimpleIntegerProperty(ticketCategory.totalTickets)
    val remainingTickets = SimpleIntegerProperty(ticketCategory.remainingTickets)
    val amount = SimpleIntegerProperty(1)
}