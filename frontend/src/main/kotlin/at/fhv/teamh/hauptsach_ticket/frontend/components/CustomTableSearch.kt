package at.fhv.teamh.hauptsach_ticket.frontend.components

import at.fhv.teamh.hauptsach_ticket.frontend.app.Defaults.autoResize
import at.fhv.teamh.hauptsach_ticket.frontend.app.Defaults.createCellFactory
import at.fhv.teamh.hauptsach_ticket.frontend.app.Defaults.maxAvailableSize
import at.fhv.teamh.hauptsach_ticket.frontend.communication.RemoteFacade
import at.fhv.teamh.hauptsach_ticket.frontend.components.CustomModules.addButtonFactory
import at.fhv.teamh.hauptsach_ticket.frontend.controller.SearchTableDataController
import at.fhv.teamh.hauptsach_ticket.frontend.controller.ShoppingCartController
import at.fhv.teamh.hauptsach_ticket.frontend.data.Event
import at.fhv.teamh.hauptsach_ticket.frontend.data.TicketCategory
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView
import javafx.collections.FXCollections
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.TableRow
import javafx.scene.paint.Color
import javafx.scene.text.FontWeight
import javafx.util.Callback
import tornadofx.*


class CustomTableSearch : View() {

    private val searchController: SearchTableDataController by inject()
    private val cartController: ShoppingCartController by inject()


    override val root = vbox {
        tableview(searchController.data) {
            autoResize()
            prefHeight = maxAvailableSize

            column("Series", Event::series)
            column("Artist", Event::artist)
            column("Name", Event::name)
            column("Location", Event::location)
            column("Genre", Event::gerne)
            column("Date", Event::date)

            rowFactory = Callback {
                val row = TableRow<Event>()
                row.onDoubleClick {
                    val ticketCategory = RemoteFacade.getTicketsForEvent(row.item.id.value)
                    val ticketCategoryList =
                        FXCollections.observableArrayList(ticketCategory.map { TicketCategory(it) })

                    dialog("Ticket Detail  " + row.item.name.value) {
                        tableview(ticketCategoryList) {
                            autoResize()
                            minHeight = 200.0

                            column("Category", TicketCategory::name)
                            column("Price", TicketCategory::price)
                            column("Total Tickets", TicketCategory::totalTickets)
                            column("Remaining Tickets", TicketCategory::remainingTickets)
                            column("Amount", TicketCategory::amount) {
                                cellFactory = createCellFactory {
                                    textfield {
                                        bind(tableRow.item.amount)
                                    }
                                }
                            }
                            column("Action", Unit::class) {
                                cellFactory = addButtonFactory(cartController)
                            }
                        }
                        hbox {
                            spacing = 10.0
                            padding = Insets(10.0, 0.0, 10.0, 10.0)
                            button("Close") {
                                action {
                                    close()
                                }
                                style {
                                    backgroundColor += Color.RED
                                    fontSize = 20.px
                                    fontWeight = FontWeight.BOLD
                                }
                            }
                        }
                    }

                }
                row
            }
            columns.forEach {
                attachTo(it)
            }
        }
        hbox {
            spacing = 10.0
            padding = Insets(10.0, 0.0, 10.0, 0.0)
            alignment = Pos.CENTER
            maxHeight = 50.0

            button {
                graphic = FontAwesomeIconView(FontAwesomeIcon.ARROW_LEFT).apply {
                    glyphSize = 32
                }
                isDisable = true

                searchController.currentPage.onChange {
                    isDisable = it == 0
                }

                action {
                    with(searchController) {
                        if (currentPage.value != 0) {
                            currentPage.value -= itemsPerPage
                            data.setAll(RemoteFacade.searchEvents(
                                lastSearchString.value,
                                eventName.value,
                                eventGenre.value,
                                eventDate.value,
                                eventNumber = currentPage.value,
                                eventsPerPage = itemsPerPage
                            ).map { Event(it) })
                        }
                    }

                }
            }

            button {
                graphic = FontAwesomeIconView(FontAwesomeIcon.ARROW_RIGHT).apply {
                    glyphSize = 32
                }

                isDisable = RemoteFacade.totalEvents() <= searchController.itemsPerPage

                var size = RemoteFacade.totalEvents()

                searchController.currentPage.onChange {
                    isDisable = it + searchController.itemsPerPage >= size
                }


                action {
                    with(searchController) {
                        if (data.isNotEmpty()) {
                            currentPage.value += itemsPerPage
                            data.setAll(RemoteFacade.searchEvents(
                                lastSearchString.value,
                                eventName.value,
                                eventGenre.value,
                                eventDate.value,
                                eventNumber = currentPage.value,
                                eventsPerPage = itemsPerPage
                            ).map { Event(it) })

                            size = RemoteFacade.totalSearchEvents(
                                lastSearchString.value,
                                eventName.value,
                                eventGenre.value,
                                eventDate.value,
                            )
                        }
                    }
                }
            }
        }
    }
}
