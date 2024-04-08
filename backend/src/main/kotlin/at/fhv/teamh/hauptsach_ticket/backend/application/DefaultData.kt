package at.fhv.teamh.hauptsach_ticket.backend.application

import at.fhv.teamh.hauptsach_ticket.backend.domain.*
import java.sql.Date
import java.time.LocalDate
import java.util.*

@Suppress("MemberVisibilityCanBePrivate", "unused")
object DefaultData {
    private const val DEFAULT = "(default)"
    private const val DEFAULT_ARTIST = "(none)"
    private const val DEFAULT_SERIES_NAME = "(no series)"
    private val DEFAULT_ID = UUID.fromString("00000000-0000-0000-0000-000000000000")
    private val DEFAULT_DATE = LocalDate.of(1970, 1, 1)

    val location = Location(
        id = DEFAULT_ID,
        address = DEFAULT,
        building = DEFAULT,
        room = DEFAULT,
    )
    val organiser = Organiser(
        id = DEFAULT_ID,
        name = DEFAULT,
        address = DEFAULT,
        eMail = DEFAULT,
        telephone = DEFAULT,
    )
    val series = Series(
        id = DEFAULT_ID,
        name = DEFAULT_SERIES_NAME,
        artist = DEFAULT_ARTIST,
    )
    val event = Event(
        id = DEFAULT_ID,
        name = DEFAULT,
        date = DEFAULT_DATE,
        genre = DEFAULT,
        location = location,
        series = series,
    )
    val ticketCategory = TicketCategory(
        id = DEFAULT_ID,
        name = DEFAULT,
        price = 00.00,
        totalTickets = -1,
        remainingTickets = -1,
    )
    val account = Account(
        id = DEFAULT_ID,
        customerId = -1
    )
    val order = Order(
        id = DEFAULT_ID,
        account = account,
        billingAddress = DEFAULT,
        orderDate = Date.valueOf(DEFAULT_DATE),
    )
    val ticket = Ticket(
        id = DEFAULT_ID,
        ticketNumber = -1,
        ticketCategory = ticketCategory,
    )
    val ticketNumber = TicketNumber(
        id = DEFAULT_ID,
        number = -1,
        available = false,
    )
    val payment = Payment(
        id = DEFAULT_ID,
        details = DEFAULT,
        order = order,
        paidOn = Date.valueOf(DEFAULT_DATE),
        paymentNumber = 0,
    )
}