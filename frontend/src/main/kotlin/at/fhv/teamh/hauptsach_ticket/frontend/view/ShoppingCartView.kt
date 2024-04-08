package at.fhv.teamh.hauptsach_ticket.frontend.view

import at.fhv.teamh.hauptsach_ticket.frontend.components.CustomCheckoutBar
import at.fhv.teamh.hauptsach_ticket.frontend.components.CustomTableShoppingCart
import at.fhv.teamh.hauptsach_ticket.frontend.components.CustomToolBar
import tornadofx.View
import tornadofx.vbox

class ShoppingCartView : View("Shopping Cart") {
    override val root = vbox {
        add(CustomToolBar())
        add(CustomTableShoppingCart())
        add(CustomCheckoutBar())
    }
}