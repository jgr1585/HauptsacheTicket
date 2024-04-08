package at.fhv.teamh.hauptsach_ticket.backend.services.implementation

import at.fhv.teamh.hauptsach_ticket.backend.application.BeansHandler.injectEJB
import at.fhv.teamh.hauptsach_ticket.backend.application.DefaultData
import at.fhv.teamh.hauptsach_ticket.backend.application.DtoConverter.toDto
import at.fhv.teamh.hauptsach_ticket.backend.application.DtoConverter.toEntity
import at.fhv.teamh.hauptsach_ticket.backend.domain.Order
import at.fhv.teamh.hauptsach_ticket.backend.infrastructure.PaymentRepository
import at.fhv.teamh.hauptsach_ticket.backend.services.PaymentService
import at.fhv.teamh.hauptsach_ticket.backend.services.remote.CustomerDBService
import at.fhv.teamh.hauptsach_ticket.library.dto.OrderDTO
import at.fhv.teamh.hauptsach_ticket.library.dto.PaymentDTO
import jakarta.ejb.Local
import jakarta.ejb.Singleton
import java.util.*

@Local(PaymentService::class)
@Singleton
open class PaymentServiceImpl : PaymentService {

    private val paymentRepository: PaymentRepository by injectEJB()

    override fun generatePaymentForOrder(order: Order) =
        paymentRepository.generatePaymentForOrder(order).toDto()

    override fun getPaymentByOrderId(id: UUID) =
        paymentRepository.getPaymentByOrderId(id).toDto()

    override fun validateCardNumber(customerId: Int, cardNumber: Long): Boolean {
        val customerCardNumber = CustomerDBService.findCustomerById(customerId.toLong()).creditCardNumber
        return customerCardNumber.equals(cardNumber.toString())
    }

    override fun payAsCustomer(customerId: Int, cardNumber: Long, order: OrderDTO): PaymentDTO {
        if (validateCardNumber(customerId, cardNumber)) return generatePaymentForOrder(order.toEntity())
        return DefaultData.payment.toDto()
    }
}