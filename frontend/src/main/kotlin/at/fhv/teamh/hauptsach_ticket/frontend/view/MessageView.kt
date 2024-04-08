package at.fhv.teamh.hauptsach_ticket.frontend.view

import at.fhv.teamh.hauptsach_ticket.frontend.components.CustomToolBar
import at.fhv.teamh.hauptsach_ticket.frontend.components.MessageBoard
import tornadofx.View
import tornadofx.vbox

class MessageView : View("Message") {

    override val root = vbox {
        add(CustomToolBar())
        add(MessageBoard())
    }
}
