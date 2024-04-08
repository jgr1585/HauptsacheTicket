package at.fhv.teamh.hauptsach_ticket.backend.infrastructure

import at.fhv.teamh.hauptsach_ticket.backend.application.DefaultData
import at.fhv.teamh.hauptsach_ticket.backend.application.PersistenceManager
import at.fhv.teamh.hauptsach_ticket.backend.application.TestData
import at.fhv.teamh.hauptsach_ticket.backend.domain.Order
import at.fhv.teamh.hauptsach_ticket.backend.domain.Ticket
import at.fhv.teamh.hauptsach_ticket.backend.infrastructure.implementation.OrderRepositoryImpl
import at.fhv.teamh.hauptsach_ticket.library.enums.OrderStatus
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.sql.Date
import java.time.LocalDate
import java.util.*
import kotlin.test.assertEquals

class OrderRepositoryTest {
    private val orderRepository = OrderRepositoryImpl()
    private lateinit var testData: TestData

    @BeforeEach
    fun initTest() {
        testData = TestData()
    }

    @Test
    fun `GIVEN noOrders WHEN getAllOrders THEN returnEmptyList`() {
        //GIVEN

        //WHEN
        val ordersActual = orderRepository.getOrders()

        //Then
        assertEquals(listOf(), ordersActual)
    }

    @Test
    fun `GIVEN threeOrders WHEN getAllOrders THEN threeOrderList`() {
        //GIVEN
        val orderExpected1 = Order(
            account = DefaultData.account, billingAddress = "test",
            orderDate = Date.valueOf(LocalDate.now())
        )
        val orderExpected2 = Order(
            account = DefaultData.account, billingAddress = "test",
            orderDate = Date.valueOf(LocalDate.now())
        )
        val orderExpected3 = Order(
            account = DefaultData.account, billingAddress = "test",
            orderDate = Date.valueOf(LocalDate.now())
        )
        val ordersExpected = listOf(orderExpected1, orderExpected2, orderExpected3)

        val em = PersistenceManager.getEntityManagerInstance()
        em.transaction.begin()
        ordersExpected.forEach { em.persist(it) }
        em.flush()
        em.transaction.commit()
        em.clear()
        em.close()

        //WHEN
        val ordersActual = orderRepository.getOrders()

        //Then
        assertEquals(ordersExpected, ordersActual)
    }

    @Test
    fun `GIVEN order WHEN getOrderById THEN returnOrder`() {
        //GIVEN
        val id = UUID.randomUUID()
        val orderExpected = Order(
            id = id, account = DefaultData.account, billingAddress = "test",
            orderDate = Date.valueOf(LocalDate.now())
        )

        val em = PersistenceManager.getEntityManagerInstance()
        em.transaction.begin()
        em.persist(orderExpected)
        em.flush()
        em.transaction.commit()
        em.clear()
        em.close()

        //WHEN
        val orderActual = orderRepository.getOrderById(id)

        //THEN
        assertEquals(orderExpected, orderActual)
    }

    @Test
    fun `GIVEN noOrder WHEN CreateOrder AND GetOrders THEN returnOneOrder`() {
        //GIVEN
        val orderAmountExpected = 1

        //WHEN
        orderRepository.createOrder()
        val orders = orderRepository.getOrders()
        println(orders)
        val orderAmountActual = orders.size

        //THEN
        assertEquals(orderAmountExpected, orderAmountActual)
    }

    @Test
    fun `GIVEN noOrder WHEN getOrderById THEN returnDefaultOrder`() {
        //GIVEN
        val orderExpected = DefaultData.order

        //WHEN
        val orderActual = orderRepository.getOrderById(UUID.randomUUID())

        //THEN
        assertEquals(orderExpected, orderActual)
    }

    @Test
    fun `GIVEN noOrder WHEN addTicketsToOrder AND getOrders THEN returnOneOrder`() {
        //GIVEN
        val ticketId = UUID.randomUUID()
        val ticketToAdd = Ticket(id = ticketId, ticketNumber = 1, ticketCategory = testData.szeneDay2TicketCategory1)
        val orderAmountExpected = 1

        //WHEN
        orderRepository.addTicketsToOrder(listOf(ticketToAdd.id), UUID.randomUUID())
        val orderAmountActual = orderRepository.getOrders().size

        //THEN
        assertEquals(orderAmountExpected, orderAmountActual)
    }

    @Test
    fun `GIVEN order AND threeTickets WHEN addTicketsToOrder AND getOrderById THEN returnOrderWithThreeTickets`() {
        //GIVEN
        val ticketId1 = UUID.randomUUID()
        val ticketId2 = UUID.randomUUID()
        val ticketId3 = UUID.randomUUID()
        val ticketsExpected = listOf(
            Ticket(id = ticketId1, ticketCategory = testData.szeneDay2TicketCategory1, ticketNumber = 1),
            Ticket(id = ticketId2, ticketCategory = testData.szeneDay2TicketCategory1, ticketNumber = 2),
            Ticket(id = ticketId3, ticketCategory = testData.szeneDay2TicketCategory1, ticketNumber = 3),
        )
        val order = Order(
            orderDate = Date.valueOf(LocalDate.now()),
            billingAddress = "(default)",
            account = DefaultData.account
        )

        val em = PersistenceManager.getEntityManagerInstance()
        em.transaction.begin()
        em.persist(order)
        ticketsExpected.forEach { em.persist(it) }
        em.flush()
        em.transaction.commit()
        em.clear()
        em.close()

        //WHEN
        orderRepository.addTicketsToOrder(listOf(ticketId1, ticketId2, ticketId3), order.id)
        val ticketsActual = orderRepository.getOrderById(order.id).tickets

        //THEN
        assertEquals(ticketsExpected, ticketsActual)
    }

    @Test
    fun `GIVEN noOrder WHEN getOrdersByCustomerId THEN returnEmptyList`() {
        //GIVEN
        val ordersExpected = listOf<Order>()

        //WHEN
        val ordersActual = orderRepository.getOrdersByCustomerId(1)

        //THEN
        assertEquals(ordersExpected, ordersActual)
    }

    @Test
    fun `GIVEN orderWithCustomer WHEN getOrdersByCustomerId THEN returnOrder`() {
        //GIVEN
        val customerId = 1
        val ticketId1 = UUID.randomUUID()
        val ticketId2 = UUID.randomUUID()
        val ticketId3 = UUID.randomUUID()
        val ticketsExpected = listOf(
            Ticket(id = ticketId1, ticketCategory = testData.szeneDay2TicketCategory1, ticketNumber = 1),
            Ticket(id = ticketId2, ticketCategory = testData.szeneDay2TicketCategory1, ticketNumber = 2),
            Ticket(id = ticketId3, ticketCategory = testData.szeneDay2TicketCategory1, ticketNumber = 3),
        )
        val orderExpected = Order(
            orderDate = Date.valueOf(LocalDate.now()),
            billingAddress = "(default)",
            account = DefaultData.account,
            customerId = customerId,
        )

        val em = PersistenceManager.getEntityManagerInstance()
        em.transaction.begin()
        em.persist(orderExpected)
        ticketsExpected.forEach { em.persist(it) }
        em.flush()
        em.transaction.commit()
        em.clear()
        em.close()

        //WHEN
        val orderActual = orderRepository.getOrdersByCustomerId(customerId)

        //THEN
        assertEquals(orderExpected, orderActual[0])
    }

    @Test
    fun `GIVEN noOrderWithCustomer WHEN getOrdersByCustomerId THEN returnEmptyList`() {
        //GIVEN
        val ordersExpected = listOf<Order>()

        //WHEN
        val orderActual = orderRepository.getOrdersByCustomerId(21)

        //THEN
        assertEquals(ordersExpected, orderActual)
    }

    @Test
    fun `GIVEN openOrder WHEN closeOrder THEN orderIsClosed`() {
        //GIVEN
        val openOrder = orderRepository.createOrder()
        val statusExpected = OrderStatus.Closed

        //WHEN
        orderRepository.closeOrder(openOrder.id)
        val orderActual = orderRepository.getOrderById(openOrder.id)

        //THEN
        assertEquals(statusExpected, orderActual.status)
    }

    @Test
    fun `GIVEN closedOrder WHEN markOrderAsCanceled THEN orderIsCanceled`() {
        //GIVEN
        val openOrder = orderRepository.createOrder()
        orderRepository.closeOrder(openOrder.id)
        val statusExpected = OrderStatus.Canceled

        //WHEN
        orderRepository.markOrderAsCanceled(openOrder.id)
        val orderActual = orderRepository.getOrderById(openOrder.id)

        //THEN
        assertEquals(statusExpected, orderActual.status)
    }

    @Test
    fun `GIVEN order WHEN addCustomerToOrder THEN returnOrderWithCustomer`() {
        //GIVEN
        val order = orderRepository.createOrder()
        val customerIdExpected = 1

        //WHEN
        orderRepository.addCustomerToOrder(order.id, customerIdExpected)

        //THEN
        val orderActual = orderRepository.getOrderById(order.id)
        assertEquals(customerIdExpected, orderActual.customerId)
    }
}