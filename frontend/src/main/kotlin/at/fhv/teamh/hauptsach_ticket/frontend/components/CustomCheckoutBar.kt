package at.fhv.teamh.hauptsach_ticket.frontend.components

import at.fhv.teamh.hauptsach_ticket.frontend.components.CustomModules.priceTextField
import at.fhv.teamh.hauptsach_ticket.frontend.controller.ShoppingCartController
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.Alert
import javafx.scene.control.ButtonType
import javafx.scene.paint.Color
import javafx.scene.text.FontWeight
import org.controlsfx.control.Notifications
import tornadofx.*

class CustomCheckoutBar : View() {
    private val cartController: ShoppingCartController by inject()
    private val checkoutDialog: CustomCheckoutDialog by inject()

    override val root = hbox {
        spacing = 10.0
        alignment = Pos.CENTER
        padding = Insets(10.0, 0.0, 10.0, 0.0)

        priceTextField(cartController.totalPrice)

        button("Empty Cart") {
            action {
                val alert = Alert(
                    Alert.AlertType.CONFIRMATION,
                    "Are you sure you want to remove all events?",
                    ButtonType.YES,
                    ButtonType.NO
                )
                alert.showAndWait()
                if (alert.result == ButtonType.YES) {
                    cartController.removeAll()
                    Notifications.create()
                        .title("All Events")
                        .text("Are removed")
                        .darkStyle()
                        .showInformation()
                }
            }
            prefHeight = 64.0
            style {
                backgroundColor += Color.RED
                fontWeight = FontWeight.BOLD
                fontSize = 20.px
            }
        }

        button("Checkout") {
            action {
                dialog {
                    this.replaceWith(checkoutDialog.root)
                }
            }

            prefHeight = 64.0
            style {
                backgroundColor += Color.BLUE
                fontWeight = FontWeight.BOLD
                fontSize = 20.px
            }
        }
    }
}