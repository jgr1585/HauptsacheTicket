package at.fhv.teamh.hauptsach_ticket.backend.application

import at.fhv.teamh.hauptsach_ticket.backend.application.BeansHandler.injectEJB
import at.fhv.teamh.hauptsach_ticket.backend.domain.*
import at.fhv.teamh.hauptsach_ticket.backend.infrastructure.OrderRepository
import at.fhv.teamh.hauptsach_ticket.backend.infrastructure.TicketCategoryRepository
import at.fhv.teamh.hauptsach_ticket.library.dto.*

@Suppress("unused")
object DtoConverter {

    private val ticketCategoryRepository: TicketCategoryRepository by injectEJB()
    private val orderRepository: OrderRepository by injectEJB()

    fun Event.toDto(): EventDTO {
        return EventDTO(
            id = this.id,
            name = this.name,
            date = this.date,
            ticketReservation = this.ticketReservation,
            series = this.series.toDto(),
            location = this.location.toDto(),
            genre = this.genre,
        )
    }

    fun Ticket.toDto(): TicketDTO {
        return TicketDTO(
            id = this.id,
            ticketNumber = this.ticketNumber,
            ticketCategory = this.ticketCategory.toDto(),
        )
    }

    fun TicketCategory.toDto(): TicketCategoryDTO {
        return TicketCategoryDTO(
            id = this.id,
            name = this.name,
            price = this.price,
            totalTickets = this.totalTickets,
            remainingTickets = this.remainingTickets,
            availableTicketNumbers = ticketCategoryRepository.getTicketNumbers(this.id)
                .filter { it.available }
                .map { it.number }
        )
    }

    fun Series.toDto(): SeriesDTO {
        return SeriesDTO(
            id = this.id,
            name = this.name,
            artist = this.artist,
        )
    }

    fun Location.toDto(): LocationDTO {
        return LocationDTO(
            id = this.id,
            address = this.address,
            building = this.building,
            room = this.room,
        )
    }

    fun AccountDTO.toEntity(): Account {
        return Account(
            id = this.id,
            customerId = this.customerId,
        )
    }

    fun PaymentDTO.toEntity(): Payment {
        return Payment(
            id = this.id,
            details = this.details,
            order = orderRepository.getOrderById(this.id),
            paidOn = this.paidOn,
            paymentNumber = this.paymentNumber,
        )
    }

    fun OrderDTO.toEntity(): Order {
        return Order(
            id = this.id,
            orderDate = this.orderDate,
            billingAddress = this.billingAddress,
            status = this.status,
            account = DefaultData.account,
        )
    }

    fun TicketCategoryDTO.toEntity(): TicketCategory {
        return TicketCategory(
            id = this.id,
            name = this.name,
            price = this.price,
            totalTickets = this.totalTickets,
            remainingTickets = this.remainingTickets,
        )
    }

    fun Topic.toDto(): TopicDTO {
        return TopicDTO(
            name = this.name
        )
    }

    fun Order.toDto(): OrderDTO {
        return OrderDTO(
            id = this.id,
            accountId = this.account.id,
            billingAddress = this.billingAddress,
            orderDate = this.orderDate,
            status = this.status,
        )
    }

    fun Payment.toDto(): PaymentDTO {
        return PaymentDTO(
            id = this.id,
            details = this.details,
            paidOn = this.paidOn,
            orderId = this.order.id,
            paymentNumber = this.paymentNumber,
        )
    }
}