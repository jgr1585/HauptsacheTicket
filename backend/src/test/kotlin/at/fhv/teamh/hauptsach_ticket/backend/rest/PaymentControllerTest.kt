package at.fhv.teamh.hauptsach_ticket.backend.rest

import at.fhv.teamh.hauptsach_ticket.backend.application.BeansHandler.injectEJB
import at.fhv.teamh.hauptsach_ticket.backend.application.DtoConverter.toDto
import at.fhv.teamh.hauptsach_ticket.backend.domain.DomainFactory
import at.fhv.teamh.hauptsach_ticket.backend.rest.auth.WebUser
import at.fhv.teamh.hauptsach_ticket.backend.rest.controller.PaymentController
import at.fhv.teamh.hauptsach_ticket.backend.services.OrderService
import at.fhv.teamh.hauptsach_ticket.backend.services.PaymentService
import at.fhv.teamh.hauptsach_ticket.library.dto.CustomerDTO
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import java.util.*
import kotlin.test.assertEquals

class PaymentControllerTest {
    private val paymentService: PaymentService by injectEJB()
    private val orderService: OrderService by injectEJB()
    private val paymentController = PaymentController()

    @Test
    fun `GIVEN order WHEN generatePaymentForOrder THEN returnPayment`() {
        //GIVEN
        val order = with (DomainFactory) {
            createOrder()
        }
        val paymentExpected = with (DomainFactory) {
            createPayment()
        }.toDto()

        `when`(paymentService.generatePaymentForOrder(order)).thenReturn(paymentExpected)

        //WHEN
        val paymentActual = paymentController.generatePaymentForOrder(order)

        //THEN
        assertEquals(paymentExpected, paymentActual)
    }

    @Test
    fun `GIVEN orderId WHEN getPaymentByOrderId THEN returnPayment`() {
        //GIVEN
        val orderId = UUID.randomUUID()
        val paymentExpected = with (DomainFactory) {
            createPayment()
        }.toDto()

        `when`(paymentService.getPaymentByOrderId(orderId)).thenReturn(paymentExpected)

        //WHEN
        val paymentActual = paymentController.getPaymentByOrderId(orderId)

        //THEN
        assertEquals(paymentExpected, paymentActual)
    }

    @Test
    fun `GIVEN orderId AND cardNumber WHEN payAsCustomer THEN returnPayment`() {
        //GIVEN
        val cardNumber = 1234567890123456
        val customerId = 1

        val clazz = paymentController::class.java
        with(clazz.getDeclaredField("authenticatedUser")) {
            isAccessible = true
            set(paymentController, WebUser(CustomerDTO(
                customerId,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                cardNumber.toString(),
                null,
                null,
                null,
            )
            )
            )
        }

        val paymentExpected = with(DomainFactory) {
            createPayment()
        }.toDto()
        val orderDTO = with(DomainFactory) {
            createOrder()
        }.toDto()

        `when`(orderService.searchOrderById(orderDTO.id)).thenReturn(orderDTO)
        `when`(paymentService.payAsCustomer(customerId, cardNumber, orderDTO)).thenReturn(paymentExpected)

        //WHEN
        val paymentActual = paymentController.payAsCustomer(cardNumber, orderDTO.id)

        //THEN
        assertEquals(paymentExpected, paymentActual)
    }
}