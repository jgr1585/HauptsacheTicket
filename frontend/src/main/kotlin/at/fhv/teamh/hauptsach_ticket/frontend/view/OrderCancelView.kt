package at.fhv.teamh.hauptsach_ticket.frontend.view

import at.fhv.teamh.hauptsach_ticket.frontend.components.CancelOrder
import at.fhv.teamh.hauptsach_ticket.frontend.components.CustomToolBar
import tornadofx.View
import tornadofx.vbox

class OrderCancelView : View("Cancel") {
    override val root = vbox {
        add(CustomToolBar())
        add(CancelOrder())
    }
}