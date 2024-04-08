package at.fhv.teamh.hauptsach_ticket.backend.domain

import java.sql.Date
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

object DomainFactory {
    fun createEvent(): Event {
        val uuid = UUID.randomUUID()

        return Event(
            id = uuid,
            name = "Event $uuid",
            date = LocalDate.now(),
            genre = "Genre $uuid",
            location = createLocation(),
        )
    }

        private fun createLocation(): Location {
            val uuid = UUID.randomUUID()

            return Location(
                id = uuid,
                address = "Address $uuid",
                building = "Building $uuid",
                room = "Room $uuid",
            )
        }

        fun createTicket(): Ticket {
            val uuid = UUID.randomUUID()

            return Ticket(
                id = uuid,
                ticketNumber = uuid.mostSignificantBits.toInt(),
                ticketCategory = createTicketCategory(),
                order = createOrder(),
            )
        }

        fun createTicketCategory(event: Event? = null): TicketCategory {
            val uuid = UUID.randomUUID()

            return TicketCategory(
                id = uuid,
                name = "TicketCategory $uuid",
                price = 10.0,
                totalTickets = 10,
                event = event ?: createEvent(),
            )
        }

        fun createOrder() : Order {
            val uuid = UUID.randomUUID()

            return Order(
                id = uuid,
                orderDate = Date.valueOf(LocalDate.now()),
                billingAddress = "BillingAddress $uuid",
                account = createAccount(),
            )
        }

        private fun createAccount(): Account {
            val uuid = UUID.randomUUID()

            return Account(
                id = uuid,
                customerId = uuid.mostSignificantBits.toInt(),
            )
        }

        fun createPayment(): Payment {
            val uuid = UUID.randomUUID()

            return Payment(
                id = uuid,
                paidOn = Date.valueOf(LocalDate.now()),
                details = "",
                order = createOrder(),
                paymentNumber = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)
            )
        }
}