package at.fhv.teamh.hauptsach_ticket.backend.services.implementation

import at.fhv.teamh.hauptsach_ticket.backend.application.BeansHandler.injectEJB
import at.fhv.teamh.hauptsach_ticket.backend.application.DtoConverter.toDto
import at.fhv.teamh.hauptsach_ticket.backend.infrastructure.OrderRepository
import at.fhv.teamh.hauptsach_ticket.backend.infrastructure.PaymentRepository
import at.fhv.teamh.hauptsach_ticket.backend.infrastructure.TicketCategoryRepository
import at.fhv.teamh.hauptsach_ticket.backend.infrastructure.TicketRepository
import at.fhv.teamh.hauptsach_ticket.backend.services.OrderService
import at.fhv.teamh.hauptsach_ticket.library.dto.OrderDTO
import jakarta.ejb.Local
import jakarta.ejb.Singleton
import java.util.*

@Local(OrderService::class)
@Singleton
open class OrderServiceImpl : OrderService {

    private val orderRepository: OrderRepository by injectEJB()
    private val ticketCategoryRepository: TicketCategoryRepository by injectEJB()
    private val ticketRepository: TicketRepository by injectEJB()
    private val paymentRepository: PaymentRepository by injectEJB()

    override fun checkoutFromCart(ticketIds: List<UUID>, customerId: Int): OrderDTO {
        val order = orderRepository.createOrder(customerId)
        orderRepository.addTicketsToOrder(ticketIds, order.id)
        orderRepository.closeOrder(order.id)
        paymentRepository.generatePaymentForOrder(order)

        ticketIds
            .map { ticketRepository.getTicketById(it) }
            .forEach { ticketCategoryRepository.markTicketAsUnavailable(it.ticketCategory.id, it.ticketNumber) }

        return order.toDto()
    }

    override fun createOrderFromCart(ticketIds: List<UUID>, customerId: Int): OrderDTO {
        val order = orderRepository.createOrder(customerId)
        orderRepository.addTicketsToOrder(ticketIds, order.id)
        orderRepository.closeOrder(order.id)

        ticketIds
            .map { ticketRepository.getTicketById(it) }
            .forEach { ticketCategoryRepository.markTicketAsUnavailable(it.ticketCategory.id, it.ticketNumber) }



        return order.toDto()
    }

    override fun getOrdersByCustomerId(customerId: Int): List<OrderDTO> =
        orderRepository.getOrdersByCustomerId(customerId).map { it.toDto() }

    override fun searchOrderById(orderId: UUID) =
        orderRepository.getOrderById(orderId).toDto()

    override fun markOrderAsCanceled(id: UUID) {
        val order = orderRepository.markOrderAsCanceled(id)
        val tickets = order.tickets
        val ticketCategories = tickets.map { it.ticketCategory }.distinct()

        for (category in ticketCategories) {
            ticketCategoryRepository.makeTicketNumbersAvailable(
                category.id,
                tickets
                    .filter { it.ticketCategory == category }
                    .map { it.ticketNumber })
        }
    }
}