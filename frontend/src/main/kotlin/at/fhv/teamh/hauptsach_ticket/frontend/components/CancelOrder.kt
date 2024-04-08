package at.fhv.teamh.hauptsach_ticket.frontend.components

import at.fhv.teamh.hauptsach_ticket.frontend.app.Defaults.autoResize
import at.fhv.teamh.hauptsach_ticket.frontend.app.Defaults.createCellFactory
import at.fhv.teamh.hauptsach_ticket.frontend.app.Defaults.maxAvailableSize
import at.fhv.teamh.hauptsach_ticket.frontend.communication.RemoteFacade
import at.fhv.teamh.hauptsach_ticket.frontend.components.CustomModules.searchCustomerField
import at.fhv.teamh.hauptsach_ticket.frontend.data.Customer
import at.fhv.teamh.hauptsach_ticket.frontend.data.Order
import at.fhv.teamh.hauptsach_ticket.frontend.data.Payment
import at.fhv.teamh.hauptsach_ticket.library.enums.OrderStatus
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.paint.Color
import javafx.scene.text.FontWeight
import tornadofx.*

class CancelOrder : View() {
    private val payments: ObservableList<Payment> = FXCollections.observableArrayList()
    private val customer = Customer()

    override val root = vbox {
        hbox {
            spacing = 10.0
            alignment = Pos.CENTER
            padding = Insets(10.0, 0.0, 10.0, 0.0)



            text("Customer: ") {
                style {
                    fontSize = 20.px
                    fontWeight = FontWeight.BOLD
                }
            }

            gridpane {
                row {
                    label("ID: ") {
                        style {
                            fontSize = 20.px
                            fontWeight = FontWeight.BOLD
                        }
                    }
                    label("Name: ") {
                        style {
                            fontSize = 20.px
                            fontWeight = FontWeight.BOLD
                        }
                    }
                    label("Address: ") {
                        style {
                            fontSize = 20.px
                            fontWeight = FontWeight.BOLD
                        }
                    }
                }
                row {
                    label(customer.customerId) {
                        style {
                            fontSize = 20.px
                        }
                    }
                    label(customer.customerName) {
                        style {
                            fontSize = 20.px
                        }
                    }
                    label(customer.customerAddress) {
                        style {
                            fontSize = 20.px
                        }
                    }
                }
            }
        }

        searchCustomerField(customer) { property ->
            val customerDTO = RemoteFacade.searchCustomerByName(property.value).firstOrNull()
            customer.customerDTO = customerDTO
            payments.clear()
            val orders = RemoteFacade.getOrdersByCustomerId(customerDTO?.id ?: -1).map { Order(it) }
            payments.addAll(orders.map {
                val paymentDTO = RemoteFacade.getPaymentByOrderId(it.id.value)
                Payment(paymentDTO, it)
            })
        }

        tableview(payments) {
            autoResize()
            prefHeight = maxAvailableSize

            column("Payment Number", Payment::paymentNumber)
            column("Date", Payment::date)
            column("Status", Payment::status)
            column("Action", Unit::class) {
                cellFactory = createCellFactory {
                    button("Cancel") {
                        graphic = FontAwesomeIconView(FontAwesomeIcon.REMOVE)
                        action {
                            RemoteFacade.markOrderAsCanceled(tableRow.item.order.value.id.value)
                            tableRow.item.status.value = OrderStatus.Canceled
                            isDisable = true
                        }

                        if (tableRow.item != null)
                            isDisable = tableRow.item.status.value == OrderStatus.Canceled

                        style {
                            backgroundColor += Color.RED
                            fontWeight = FontWeight.BOLD
                        }
                    }
                }
            }
        }
    }
}

