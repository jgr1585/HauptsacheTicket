package at.fhv.teamh.hauptsach_ticket.frontend.view

import at.fhv.teamh.hauptsach_ticket.frontend.components.CustomToolBar
import at.fhv.teamh.hauptsach_ticket.frontend.components.MessageCreate
import at.fhv.teamh.hauptsach_ticket.frontend.data.Message
import tornadofx.View
import tornadofx.vbox

class CreatMessageView(message: Message? = null) : View() {

    override val root = vbox {
        add(CustomToolBar())
        add(MessageCreate(message))
    }
}