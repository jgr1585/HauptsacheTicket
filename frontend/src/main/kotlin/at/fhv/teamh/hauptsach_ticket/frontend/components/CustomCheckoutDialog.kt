package at.fhv.teamh.hauptsach_ticket.frontend.components

import at.fhv.teamh.hauptsach_ticket.frontend.communication.RemoteFacade
import at.fhv.teamh.hauptsach_ticket.frontend.components.CustomModules.eventTable
import at.fhv.teamh.hauptsach_ticket.frontend.components.CustomModules.priceTextField
import at.fhv.teamh.hauptsach_ticket.frontend.components.CustomModules.searchCustomerField
import at.fhv.teamh.hauptsach_ticket.frontend.controller.ShoppingCartController
import at.fhv.teamh.hauptsach_ticket.frontend.data.Customer
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.Alert
import javafx.scene.control.ButtonType
import javafx.scene.paint.Color
import javafx.scene.text.FontWeight
import tornadofx.*

class CustomCheckoutDialog : View() {
    private val cartController: ShoppingCartController by inject()
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
                    label("ID: ")
                    label("Name: ")
                    label("Address: ")
                }
                row {
                    label(customer.customerId)
                    label(customer.customerName)
                    label(customer.customerAddress)
                }
            }
        }

        searchCustomerField(customer) {
            val customerDTO = RemoteFacade.searchCustomerByName(it.value).firstOrNull()
            customer.customerDTO = customerDTO
        }

        eventTable(cartController)

        hbox {
            spacing = 10.0
            alignment = Pos.CENTER
            padding = Insets(10.0, 0.0, 10.0, 0.0)

            priceTextField(cartController.totalPrice)

            button("Cancel") {
                action {
                    close()
                }
                prefHeight = 64.0
                style {
                    backgroundColor += Color.RED
                    fontWeight = FontWeight.BOLD
                    fontSize = 20.px
                }
            }

            button("Checkout") {
                isDisable = customer.customerDTO == null

                action {
                    val alert = Alert(
                        Alert.AlertType.CONFIRMATION,
                        "You want to buy these tickets",
                        ButtonType.YES,
                        ButtonType.NO
                    )
                    alert.showAndWait()
                    if (alert.result == ButtonType.YES) {
                        RemoteFacade.checkoutFromCart(
                            RemoteFacade.shoppingCart.map { it.id },
                            customer.customerDTO?.id ?: -1
                        )
                        cartController.removeAll()
                    }
                    close()
                }
                prefHeight = 64.0
                style {
                    backgroundColor += Color.BLUE
                    fontWeight = FontWeight.BOLD
                    fontSize = 20.px
                }

                customer.customerId.onChange {
                    isDisable = it == null
                }
            }
        }
    }
}