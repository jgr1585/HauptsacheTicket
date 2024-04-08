package at.fhv.teamh.hauptsach_ticket.frontend.components

import at.fhv.teamh.hauptsach_ticket.frontend.app.Defaults.autoResize
import at.fhv.teamh.hauptsach_ticket.frontend.app.Defaults.createCellFactory
import at.fhv.teamh.hauptsach_ticket.frontend.communication.RemoteFacade
import at.fhv.teamh.hauptsach_ticket.frontend.controller.ShoppingCartController
import at.fhv.teamh.hauptsach_ticket.frontend.data.Customer
import at.fhv.teamh.hauptsach_ticket.frontend.data.Ticket
import at.fhv.teamh.hauptsach_ticket.frontend.data.TicketCategory
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView
import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import javafx.event.EventTarget
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.input.KeyCode
import javafx.scene.paint.Color
import javafx.scene.text.FontWeight
import javafx.util.Callback
import org.controlsfx.control.Notifications
import org.controlsfx.control.textfield.AutoCompletionBinding
import org.controlsfx.control.textfield.TextFields
import tornadofx.*

object CustomModules {
    fun EventTarget.searchCustomerField(
        customer: Customer,
        action: EventTarget.(customerSearchString: SimpleStringProperty) -> Unit,
    ) = hbox {
        spacing = 10.0
        alignment = Pos.CENTER
        padding = Insets(10.0, 0.0, 10.0, 0.0)

        val customerSearchString = SimpleStringProperty("")

        textfield(customerSearchString) {
            promptText = "search for customer"
            setOnKeyPressed { event ->
                if (event.code == KeyCode.ENTER) {
                    val customerDTO = RemoteFacade.searchCustomerByName(customerSearchString.value).firstOrNull()
                    customer.customerDTO = customerDTO
                }
            }

            TextFields.bindAutoCompletion(
                this,
                Callback<AutoCompletionBinding.ISuggestionRequest, Collection<String?>> { request ->
                    RemoteFacade.searchCustomerByName(request.userText)
                        .map { with(it) { "$familyName $givenName" } }
                        .distinct()
                })
        }

        button("Go") {
            graphic = FontAwesomeIconView(FontAwesomeIcon.SEARCH).apply {
                glyphSize = 24
                fill = Color.WHITE
            }
            setPrefSize(64.0, 64.0)

            this@button.action {
                action(customerSearchString)
            }
        }
    }

    fun EventTarget.priceTextField(price: SimpleDoubleProperty) = textfield {
        text = "Total Price: %.2f €".format(price.value)
        isEditable = false

        price.onChange {
            text = "Total Price: %.2f €".format(it)
        }
    }

    fun EventTarget.eventTable(cartController: ShoppingCartController) = tableview(cartController.items) {
        autoResize()

        column("Category", Ticket::categoryName)
        column("Event", Ticket::eventName)
        column("Date", Ticket::eventDate)
        column("Price", Ticket::totalPrice) {
            cellFormat {
                text = String.format("%.2f €", it)
            }
        }
        column("TicketNumber", Ticket::ticketNumber)
        column("Action", Unit::class) {
            cellFactory = createCellFactory {
                button("Remove") {
                    graphic = FontAwesomeIconView(FontAwesomeIcon.MINUS)
                    action {
                        cartController.removeAction(tableRow.item)
                    }
                    style {
                        backgroundColor += Color.RED
                        fontWeight = FontWeight.BOLD
                    }
                }
            }
        }
        column("Seat Selection", Unit::class) {
            cellFactory = createCellFactory {
                combobox(tableRow.item.ticketNumber) {
                    this@combobox.items = FXCollections.observableArrayList(
                        (RemoteFacade.getAvailableTicketNumbers(tableRow.item.ticketCategory.value.id.value))
                    )
                }
            }
        }

        columns.forEach {
            attachTo(it)
        }

        sortOrder.add(columns[0]) //Sort by category
    }

    fun addButtonFactory(cartController: ShoppingCartController) = createCellFactory<TicketCategory, Unit> {
        button("Add") {
            graphic = FontAwesomeIconView(FontAwesomeIcon.PLUS)
            action {
                val rows = RemoteFacade.addTicketsToCart(
                    tableRow.item.id.value,
                    tableRow.item.amount.value,
                    emptyList()
                )
                    .map { Ticket(it) }
                if (!cartController.addAll(rows)) {
                    Notifications.create()
                        .title("Event")
                        .text("Is already added")
                        .darkStyle()
                        .showInformation()
                } else {
                    Notifications.create()
                        .title("Event added")
                        .text("The event has been added to cart")
                        .darkStyle()
                        .showInformation()
                }
            }
            style {
                backgroundColor += Color.BLUE
                fontWeight = FontWeight.BOLD
            }
        }
    }
}