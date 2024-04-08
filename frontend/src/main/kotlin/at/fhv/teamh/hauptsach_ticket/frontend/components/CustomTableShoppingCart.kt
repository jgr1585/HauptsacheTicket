package at.fhv.teamh.hauptsach_ticket.frontend.components

import at.fhv.teamh.hauptsach_ticket.frontend.components.CustomModules.eventTable
import at.fhv.teamh.hauptsach_ticket.frontend.controller.ShoppingCartController
import tornadofx.View

class CustomTableShoppingCart : View() {
    private val cartController: ShoppingCartController by inject()

    override val root = eventTable(cartController)
}

