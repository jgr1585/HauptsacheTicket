package at.fhv.teamh.hauptsach_ticket.backend.infrastructure

import at.fhv.teamh.hauptsach_ticket.backend.domain.Order
import at.fhv.teamh.hauptsach_ticket.backend.domain.Payment
import jakarta.ejb.Local
import java.util.*

@Local
interface PaymentRepository {
    fun generatePaymentForOrder(order: Order): Payment
    fun getPaymentByOrderId(orderId: UUID): Payment
}