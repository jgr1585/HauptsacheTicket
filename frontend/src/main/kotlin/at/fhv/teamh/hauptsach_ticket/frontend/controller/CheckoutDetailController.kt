package at.fhv.teamh.hauptsach_ticket.frontend.controller

import at.fhv.teamh.hauptsach_ticket.frontend.data.TicketCategory
import javafx.collections.ObservableList
import tornadofx.Controller
import tornadofx.observableListOf
import tornadofx.onChange

class CheckoutDetailController : Controller() {
    var items = observableListOf<TicketCategory>()

    fun add(row: ObservableList<TicketCategory>) {
        items = row
    }

    init {
        items.onChange {
            items
        }
    }
}