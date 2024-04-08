package at.fhv.teamh.hauptsach_ticket.frontend.view


import tornadofx.View
import tornadofx.vbox

class MainView : View("Hauptsache Ticket") {

    override val root = vbox {
        add(LoginView())
    }

    fun changeView(view: View) {
        root.children.clear()
        root.add(view)
    }
}