package at.fhv.teamh.hauptsach_ticket.frontend.data

import at.fhv.teamh.hauptsach_ticket.library.dto.PaymentDTO
import javafx.beans.property.SimpleLongProperty
import javafx.beans.property.SimpleObjectProperty

class Payment(payment: PaymentDTO, order: Order) {
    val id = SimpleObjectProperty(payment.id)
    val paymentNumber = SimpleLongProperty(payment.paymentNumber)
    val order = SimpleObjectProperty(order)
    val date get() = order.value.date
    val status get() = order.value.status
}