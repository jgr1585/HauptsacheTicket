package at.fhv.teamh.hauptsach_ticket.frontend.components

import at.fhv.teamh.hauptsach_ticket.frontend.components.CustomModules.addButtonFactory
import at.fhv.teamh.hauptsach_ticket.frontend.controller.CheckoutDetailController
import at.fhv.teamh.hauptsach_ticket.frontend.controller.ShoppingCartController
import at.fhv.teamh.hauptsach_ticket.frontend.data.TicketCategory
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.TableCell
import javafx.scene.control.TableColumn
import javafx.scene.paint.Color
import javafx.scene.text.FontWeight
import javafx.util.Callback
import tornadofx.*

@Suppress("unused")
class CustomDetailDialog : View() {
    private val checkoutDetailController: CheckoutDetailController by inject()
    private val cartController: ShoppingCartController by inject()

    override val root = vbox {
        alignment = Pos.CENTER
        tableview(checkoutDetailController.items) {
            column("Category", TicketCategory::name)
            column("Price", TicketCategory::price)
            column("Total Tickets", TicketCategory::totalTickets)
            column("Remaining Tickets", TicketCategory::remainingTickets)
            column("Amount", TicketCategory::amount) {
                cellFactory =
                    Callback<TableColumn<TicketCategory, Number>, TableCell<TicketCategory, Number>> {
                        object : TableCell<TicketCategory, Number>() {
                            override fun updateItem(item: Number?, empty: Boolean) {
                                super.updateItem(item, empty)

                                graphic = if (empty || tableRow.item == null) {
                                    null
                                } else {
                                    textfield {
                                        bind(tableRow.item.amount)
                                    }
                                }
                            }
                        }
                    }
            }
            column("Action", Unit::class) {
                cellFactory = addButtonFactory(cartController)
            }
            widthProperty().onChange { event ->
                val width = event / columns.size - 1

                columns.forEach {
                    it.prefWidth = width
                }
            }
        }
        hbox {
            spacing = 10.0
            padding = Insets(10.0, 0.0, 10.0, 0.0)
            button("Close") {
                action {
                    close()
                }
                style {
                    backgroundColor += Color.RED
                    fontWeight = FontWeight.BOLD
                }
            }
        }
    }
}
