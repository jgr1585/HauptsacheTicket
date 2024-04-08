package at.fhv.teamh.hauptsach_ticket.frontend.controller

import at.fhv.teamh.hauptsach_ticket.frontend.communication.RemoteFacade
import at.fhv.teamh.hauptsach_ticket.frontend.data.Ticket
import javafx.beans.property.SimpleDoubleProperty
import javafx.scene.control.Alert
import javafx.scene.control.ButtonType
import org.controlsfx.control.Notifications
import tornadofx.Controller
import tornadofx.observableListOf
import tornadofx.onChange

class ShoppingCartController : Controller() {
    val items = observableListOf<Ticket>()
    val totalPrice = SimpleDoubleProperty(0.0)

    fun addAll(tickets: List<Ticket>): Boolean {
        return !tickets.map(this::add).any { !it }
    }

    fun add(ticket: Ticket): Boolean {
        if (!items.contains(ticket)) {
            items.add(ticket)
            return true
        }
        return false
    }

    fun removeAll() {
        RemoteFacade.removeTicketsFromCart(items.map { it.id.value })
        items.clear()
    }

    fun removeAction(item: Ticket) {
        val alert = Alert(
            Alert.AlertType.CONFIRMATION,
            "Sure you want to remove the Event",
            ButtonType.OK,
            ButtonType.CANCEL
        )
        val result = alert.showAndWait()
        if (result.isPresent && result.get() == ButtonType.OK) {
            items.remove(item)
            RemoteFacade.removeTicketsFromCart(listOf(item.id.value))
            val notification = Notifications.create()
                .title("Event removed")
                .text("The event has been removed from cart.")
                .darkStyle()
            notification.showInformation()
        }
    }

    init {
        items.onChange { ticketChange ->
            totalPrice.value = ticketChange.list.sumOf { it.totalPrice.value }
        }
    }
}