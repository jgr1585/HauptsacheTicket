package at.fhv.teamh.hauptsach_ticket.backend.services

import at.fhv.teamh.hauptsach_ticket.backend.application.BeansHandler.injectEJB
import at.fhv.teamh.hauptsach_ticket.backend.application.DtoConverter.toDto
import at.fhv.teamh.hauptsach_ticket.backend.domain.DomainFactory
import at.fhv.teamh.hauptsach_ticket.backend.infrastructure.OrderRepository
import at.fhv.teamh.hauptsach_ticket.backend.infrastructure.PaymentRepository
import at.fhv.teamh.hauptsach_ticket.backend.infrastructure.TicketCategoryRepository
import at.fhv.teamh.hauptsach_ticket.backend.infrastructure.TicketRepository
import at.fhv.teamh.hauptsach_ticket.backend.services.implementation.OrderServiceImpl
import at.fhv.teamh.hauptsach_ticket.library.enums.OrderStatus
import org.junit.jupiter.api.Test
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import kotlin.test.assertEquals

class OrderServiceTest {

    private val orderService = OrderServiceImpl()

    private val orderRepository: OrderRepository by injectEJB()
    private val paymentRepository: PaymentRepository by injectEJB()
    private val ticketRepository: TicketRepository by injectEJB()
    private val ticketCategoryRepository: TicketCategoryRepository by injectEJB()

    @Test
    fun `GIVEN TicketIds AND CustomerId WHEN checkoutFromCart THEN execute createOrder, addTicketsToOrder, closeOrder, generatePaymentForOrder, getTicketById AND markTicketAsUnavailable`() {
        //GIVEN
        val orderExpected = with(DomainFactory) {
            createOrder()
        }
        val customerId = 1
        orderExpected.customerId = customerId
        val tickets = with(DomainFactory) {
            listOf(
                createTicket(),
                createTicket(),
                createTicket(),
            )
        }
        val ticketIds = tickets.map { it.id }
        orderExpected.tickets = tickets.toMutableList()

        `when`(orderRepository.createOrder(customerId)).thenReturn(orderExpected)
        `when`(ticketRepository.getTicketById(ticketIds[0])).thenReturn(tickets[0])
        `when`(ticketRepository.getTicketById(ticketIds[1])).thenReturn(tickets[1])
        `when`(ticketRepository.getTicketById(ticketIds[2])).thenReturn(tickets[2])

        //WHEN
        orderService.checkoutFromCart(ticketIds = ticketIds, customerId = customerId)

        //THEN
        verify(orderRepository).addTicketsToOrder(ticketIds, orderExpected.id)
        verify(orderRepository).closeOrder(orderExpected.id)
        verify(paymentRepository).generatePaymentForOrder(orderExpected)

        verify(ticketCategoryRepository)
            .markTicketAsUnavailable(tickets[0].ticketCategory.id, tickets[0].ticketNumber)
        verify(ticketCategoryRepository)
            .markTicketAsUnavailable(tickets[1].ticketCategory.id, tickets[1].ticketNumber)
        verify(ticketCategoryRepository)
            .markTicketAsUnavailable(tickets[2].ticketCategory.id, tickets[2].ticketNumber)
    }

    @Test
    fun `GIVEN customerId WHEN getOrdersByCustomerId THEN returnOrders`() {
        //GIVEN
        val orders = with(DomainFactory) {
            listOf(
                createOrder(),
                createOrder(),
                createOrder(),
            )
        }
        val ordersExpected = orders.map { it.toDto() }
        `when`(orderRepository.getOrdersByCustomerId(orders[0].customerId)).thenReturn(orders)

        //WHEN
        val orderActual = orderService.getOrdersByCustomerId(orders[0].customerId)

        //THEN
        assertEquals(ordersExpected, orderActual)
    }

    @Test
    fun `GIVEN order WHEN searchOrderById THEN returnOrderDTO`() {
        //GIVEN
        val order = DomainFactory.createOrder()

        `when`(orderRepository.getOrderById(order.id)).thenReturn(order)
        val orderExpected = order.toDto()

        //WHEN
        val orderActual = orderService.searchOrderById(order.id)

        //THEN
        assertEquals(orderExpected, orderActual)
    }

    @Test
    fun `GIVEN order WHEN markOrderAsCanceled THEN returnCanceledOrder AND execute makeTicketNumbersAvailable`() {
        //GIVEN
        val order = DomainFactory.createOrder()
        val tickets = with(DomainFactory) {
            listOf(
                createTicket(),
                createTicket(),
                createTicket(),
            )
        }
        val ticketCategories = tickets.map { it.ticketCategory }
        order.tickets = tickets.toMutableList()
        order.status = OrderStatus.Canceled

        `when`(orderRepository.markOrderAsCanceled(order.id)).thenReturn(order)

        //WHEN
        orderService.markOrderAsCanceled(order.id)

        //THEN
        verify(ticketCategoryRepository)
            .makeTicketNumbersAvailable(ticketCategories[0].id, listOf(tickets[0].ticketNumber))
        verify(ticketCategoryRepository)
            .makeTicketNumbersAvailable(ticketCategories[1].id, listOf(tickets[1].ticketNumber))
        verify(ticketCategoryRepository)
            .makeTicketNumbersAvailable(ticketCategories[2].id, listOf(tickets[2].ticketNumber))
    }

    @Test
    fun `GIVEN TicketIds AND CustomerId WHEN createOrderFromCart THEN execute createOrder, addTicketsToOrder, closeOrder, getTicketById AND markTicketAsUnavailable`() {
        //GIVEN
        val orderExpected = with(DomainFactory) {
            createOrder()
        }
        val customerId = 1
        orderExpected.customerId = customerId
        val tickets = with(DomainFactory) {
            listOf(
                createTicket(),
                createTicket(),
                createTicket(),
            )
        }
        val ticketIds = tickets.map { it.id }
        orderExpected.tickets = tickets.toMutableList()

        `when`(orderRepository.createOrder(customerId)).thenReturn(orderExpected)
        `when`(ticketRepository.getTicketById(ticketIds[0])).thenReturn(tickets[0])
        `when`(ticketRepository.getTicketById(ticketIds[1])).thenReturn(tickets[1])
        `when`(ticketRepository.getTicketById(ticketIds[2])).thenReturn(tickets[2])

        //WHEN
        orderService.createOrderFromCart(ticketIds = ticketIds, customerId = customerId)

        //THEN
        verify(orderRepository).addTicketsToOrder(ticketIds, orderExpected.id)
        verify(orderRepository).closeOrder(orderExpected.id)

        verify(ticketCategoryRepository)
            .markTicketAsUnavailable(tickets[0].ticketCategory.id, tickets[0].ticketNumber)
        verify(ticketCategoryRepository)
            .markTicketAsUnavailable(tickets[1].ticketCategory.id, tickets[1].ticketNumber)
        verify(ticketCategoryRepository)
            .markTicketAsUnavailable(tickets[2].ticketCategory.id, tickets[2].ticketNumber)
    }
}