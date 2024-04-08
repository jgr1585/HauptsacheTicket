package at.fhv.teamh.hauptsach_ticket.backend.services

import at.fhv.teamh.hauptsach_ticket.backend.domain.Order
import at.fhv.teamh.hauptsach_ticket.library.dto.OrderDTO
import at.fhv.teamh.hauptsach_ticket.library.dto.PaymentDTO
import jakarta.ejb.Local
import java.util.*

@Local
interface PaymentService {
    fun generatePaymentForOrder(order: Order): PaymentDTO
    fun getPaymentByOrderId(id: UUID): PaymentDTO
    fun validateCardNumber(customerId: Int, cardNumber: Long): Boolean
    fun payAsCustomer(customerId: Int, cardNumber: Long, order: OrderDTO): PaymentDTO
}