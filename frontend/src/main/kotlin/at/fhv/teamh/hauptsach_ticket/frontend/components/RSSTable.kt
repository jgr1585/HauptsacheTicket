package at.fhv.teamh.hauptsach_ticket.frontend.components

import at.fhv.teamh.hauptsach_ticket.frontend.app.Defaults.autoResize
import at.fhv.teamh.hauptsach_ticket.frontend.app.Defaults.createCellFactory
import at.fhv.teamh.hauptsach_ticket.frontend.app.Defaults.maxAvailableSize
import at.fhv.teamh.hauptsach_ticket.frontend.communication.RSSReaderService
import at.fhv.teamh.hauptsach_ticket.frontend.data.Message
import at.fhv.teamh.hauptsach_ticket.frontend.data.RSSObject
import at.fhv.teamh.hauptsach_ticket.frontend.view.CreatMessageView
import at.fhv.teamh.hauptsach_ticket.frontend.view.MainView
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView
import javafx.application.Platform
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.scene.paint.Color
import javafx.scene.text.FontWeight
import tornadofx.*

class RSSTable : View() {
    private val rssReaderService = RSSReaderService()
    private val mainView: MainView by inject()
    private val rssObjects: ObservableList<RSSObject> = FXCollections.observableArrayList()

    override val root = tableview(rssObjects) {
        autoResize()
        prefHeight = maxAvailableSize

        Platform.runLater {
            rssObjects.addAll(rssReaderService.readRSS().map { RSSObject(it) })
        }

        column("Title", RSSObject::title)
        column("Description", RSSObject::description)
        column("Action", Unit::class) {
            cellFactory = createCellFactory {
                button("Create Message") {
                    graphic = FontAwesomeIconView(FontAwesomeIcon.PLUS)
                    action {
                        val message = Message().apply {
                            title.value = tableRow.item.title.value
                            body.value = tableRow.item.description.value
                        }
                        mainView.changeView(CreatMessageView(message))
                    }
                    style {
                        backgroundColor += Color.BLUE
                        fontWeight = FontWeight.BOLD
                    }
                }
            }
        }
    }
}