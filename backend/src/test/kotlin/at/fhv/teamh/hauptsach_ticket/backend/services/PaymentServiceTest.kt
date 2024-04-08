package at.fhv.teamh.hauptsach_ticket.backend.services

import at.fhv.teamh.hauptsach_ticket.backend.application.BeansHandler.injectEJB
import at.fhv.teamh.hauptsach_ticket.backend.domain.DomainFactory
import at.fhv.teamh.hauptsach_ticket.backend.domain.Payment
import at.fhv.teamh.hauptsach_ticket.backend.infrastructure.PaymentRepository
import at.fhv.teamh.hauptsach_ticket.backend.services.implementation.PaymentServiceImpl
import org.junit.jupiter.api.Test
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import java.sql.Date
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset
import kotlin.test.assertEquals

class PaymentServiceTest {

    private val paymentService = PaymentServiceImpl()
    private val paymentRepository: PaymentRepository by injectEJB()

    @Test
    fun `GIVEN order WHEN getPaymentByOrderId THEN returnPayment`() {
        //GIVEN
        val order = with(DomainFactory) {
            createOrder()
        }

        `when`(paymentRepository.getPaymentByOrderId(order.id))
            .thenReturn(
                Payment(
                    paidOn = Date.valueOf(LocalDate.now()),
                    details = "",
                    order = order,
                    paymentNumber = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC),
                )
            )

        //WHEN
        val orderActual = paymentService.getPaymentByOrderId(order.id).orderId

        //THEN
        verify(paymentRepository).getPaymentByOrderId(order.id)
        assertEquals(order.id, orderActual)
    }

    @Test
    fun `GIVEN order WHEN getPaymentByOrderId THEN executeGeneratePaymentForOrderInRepository`() {
        //GIVEN
        val order = DomainFactory.createOrder()

        `when`(paymentRepository.generatePaymentForOrder(order))
            .thenReturn(
                Payment(
                    paidOn = Date.valueOf(LocalDate.now()),
                    details = "",
                    order = order,
                    paymentNumber = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC),
                )
            )

        //WHEN
        paymentService.generatePaymentForOrder(order)

        //THEN
        verify(paymentRepository).generatePaymentForOrder(order)
    }
}