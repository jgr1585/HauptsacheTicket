package at.fhv.teamh.hauptsach_ticket.backend.infrastructure

import at.fhv.teamh.hauptsach_ticket.backend.domain.Order
import jakarta.ejb.Local
import java.util.*

@Local
interface OrderRepository {
    fun createOrder(customerId: Int? = null): Order
    fun getOrders(): List<Order>
    fun getOrderById(orderId: UUID): Order
    fun addTicketsToOrder(ticketIds: List<UUID>, orderId: UUID)
    fun addCustomerToOrder(orderId: UUID, customerId: Int)
    fun getOrdersByCustomerId(id: Int): List<Order>
    fun markOrderAsCanceled(id: UUID): Order
    fun closeOrder(id: UUID)
}