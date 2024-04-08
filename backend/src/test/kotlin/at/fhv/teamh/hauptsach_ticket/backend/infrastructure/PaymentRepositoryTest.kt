package at.fhv.teamh.hauptsach_ticket.backend.infrastructure

import at.fhv.teamh.hauptsach_ticket.backend.application.DefaultData
import at.fhv.teamh.hauptsach_ticket.backend.application.TestData
import at.fhv.teamh.hauptsach_ticket.backend.infrastructure.implementation.OrderRepositoryImpl
import at.fhv.teamh.hauptsach_ticket.backend.infrastructure.implementation.PaymentRepositoryImpl
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*
import kotlin.test.assertEquals

class PaymentRepositoryTest {
    private val paymentRepository = PaymentRepositoryImpl()
    private val orderRepository = OrderRepositoryImpl()
    private lateinit var testData: TestData

    @BeforeEach
    fun initTest() {
        testData = TestData()
    }

    @Test
    fun `GIVEN order WHEN generatePaymentForOrder AND getPaymentByOrderId THEN returnPayment`() {
        //GIVEN
        val order = orderRepository.createOrder()

        //WHEN
        paymentRepository.generatePaymentForOrder(order)
        val paymentActual = paymentRepository.getPaymentByOrderId(order.id)

        //THEN
        assertEquals(order.id, paymentActual.order.id)
    }

    @Test
    fun `GIVEN noPayment WHEN getPaymentByOrderId THEN returnDefaultPayment`() {
        //GIVEN
        val paymentExpected = DefaultData.payment

        //WHEN
        val paymentActual = paymentRepository.getPaymentByOrderId(UUID.randomUUID())

        //THEN
        assertEquals(paymentExpected, paymentActual)
    }
}