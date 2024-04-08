package at.fhv.teamh.hauptsach_ticket.frontend.view

import at.fhv.teamh.hauptsach_ticket.frontend.components.CustomSearchBar
import at.fhv.teamh.hauptsach_ticket.frontend.components.CustomTableSearch
import at.fhv.teamh.hauptsach_ticket.frontend.components.CustomToolBar
import tornadofx.View
import tornadofx.vbox

class SearchView : View("Search") {

    override val root = vbox {
        add(CustomToolBar())
        add(CustomSearchBar())
        add(CustomTableSearch())
    }
}