package at.fhv.teamh.hauptsach_ticket.frontend.data

import at.fhv.teamh.hauptsach_ticket.library.dto.OrderDTO
import javafx.beans.property.SimpleObjectProperty

class Order(order: OrderDTO) {
    val id = SimpleObjectProperty(order.id)
    val date = SimpleObjectProperty(order.orderDate)
    val status = SimpleObjectProperty(order.status)
}