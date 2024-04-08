package at.fhv.teamh.hauptsach_ticket.frontend.app

import at.fhv.teamh.hauptsach_ticket.frontend.view.MainView
import javafx.stage.Stage
import tornadofx.App

class MainApp: App(MainView::class, Styles::class) {
    override fun start(stage: Stage) {
        with(stage) {
            width = 1200.0
            height = 600.0
        }
        super.start(stage)
    }
}