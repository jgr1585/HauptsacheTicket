package at.fhv.teamh.hauptsach_ticket.frontend.view

import at.fhv.teamh.hauptsach_ticket.frontend.components.CustomToolBar
import at.fhv.teamh.hauptsach_ticket.frontend.components.RSSTable
import tornadofx.View
import tornadofx.vbox


class RSSFeedView : View("RSSView") {

    override val root = vbox {
        add(CustomToolBar())
        add(RSSTable())
    }
}