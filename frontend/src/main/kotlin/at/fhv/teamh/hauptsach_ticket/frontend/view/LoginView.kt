package at.fhv.teamh.hauptsach_ticket.frontend.view

import at.fhv.teamh.hauptsach_ticket.frontend.components.CustomLogin
import tornadofx.View
import tornadofx.vbox

class LoginView : View("Login") {

    override val root = vbox {
        prefWidth = 1280.0
        add(CustomLogin())
    }
}