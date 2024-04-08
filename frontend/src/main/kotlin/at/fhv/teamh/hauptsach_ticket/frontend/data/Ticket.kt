package at.fhv.teamh.hauptsach_ticket.frontend.data

import at.fhv.teamh.hauptsach_ticket.frontend.communication.RemoteFacade
import at.fhv.teamh.hauptsach_ticket.library.dto.TicketDTO
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleObjectProperty
import tornadofx.onChange

class Ticket(ticket: TicketDTO) {
    val id = SimpleObjectProperty(ticket.id)
    val ticketNumber = SimpleIntegerProperty(ticket.ticketNumber)
    val ticketCategory = SimpleObjectProperty(TicketCategory(ticket.ticketCategory))
    val totalPrice = SimpleObjectProperty(calcTotalPrice())
    private val event = Event(RemoteFacade.getEventByCategoryId(ticket.ticketCategory.id))

    val categoryName get() = ticketCategory.value.name
    val eventDate get() = event.date
    val eventName get() = event.name


    private fun calcTotalPrice(): Double {
        with(ticketCategory.value) {
            return price.value
        }
    }

    init {
        ticketCategory.onChange {
            totalPrice.value = calcTotalPrice()
        }
    }
}