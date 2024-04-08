package at.fhv.teamh.hauptsach_ticket.backend.services

import at.fhv.teamh.hauptsach_ticket.library.dto.OrderDTO
import jakarta.ejb.Local
import java.util.*

@Local
interface OrderService {
    fun checkoutFromCart(ticketIds: List<UUID>, customerId: Int): OrderDTO
    fun getOrdersByCustomerId(customerId: Int): List<OrderDTO>
    fun searchOrderById(orderId: UUID): OrderDTO
    fun markOrderAsCanceled(id: UUID)
    fun createOrderFromCart(ticketIds: List<UUID>, customerId: Int): OrderDTO
}