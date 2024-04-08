package at.fhv.teamh.hauptsach_ticket.library.communication

import at.fhv.teamh.hauptsach_ticket.library.dto.*
import at.fhv.teamh.hauptsach_ticket.library.exception.AuthenticationFailedException
import at.fhv.teamh.hauptsach_ticket.library.exception.NoPermissionException
import jakarta.ejb.Remote
import java.time.LocalDate
import java.util.*

@Remote
@Suppress("unused")
interface ApplicationClient {

    val ldapUser: LdapUserDTO

    val shoppingCart: List<TicketDTO>

    @Throws(AuthenticationFailedException::class)
    fun authenticate(username: String, password: String): Boolean

    fun getEvents(eventNumber: Int = 0, eventsPerPage: Int = 25): List<EventDTO>

    fun totalEvents(): Int

    fun getTicketsForEvent(eventId: UUID): List<TicketCategoryDTO>

    @Throws(NoPermissionException::class)
    fun searchEvents(
        searchString: String,
        eventName: String? = null,
        genre: String? = null,
        date: LocalDate? = null,
        eventNumber: Int = 0,
        eventsPerPage: Int = 25
    ): List<EventDTO>

    fun totalSearchEvents(searchString: String, eventName: String?, genre: String?, date: LocalDate?): Int

    @Throws(NoPermissionException::class)
    fun addTicketsToCart(ticketCategoryId: UUID, amount: Int, ticketNumbers: List<Int>): List<TicketDTO>

    fun getEventByCategoryId(ticketCategoryId: UUID): EventDTO

    @Throws(NoPermissionException::class)
    fun removeTicketsFromCart(ticketIds: List<UUID>)

    fun findEventName(eventName: String): List<EventDTO>

    fun findEventGenre(genre: String): List<EventDTO>

    @Throws(NoPermissionException::class)
    fun getAvailableTicketNumbers(ticketCategoryId: UUID): List<Int>

    fun checkoutFromCart(ticketIds: List<UUID>, customerId: Int = -1)

    @Throws(NoPermissionException::class)
    fun searchCustomerByName(name: String): List<CustomerDTO>

    fun logout()

    fun getActiveMQBroker(): String

    @Throws(NoPermissionException::class)
    fun searchOrderById(id: UUID): OrderDTO

    @Throws(NoPermissionException::class)
    fun markOrderAsCanceled(id: UUID)

    fun getOrdersByCustomerId(customerId: Int): List<OrderDTO>

    @Throws(NoPermissionException::class)
    fun getPaymentByOrderId(orderId: UUID): PaymentDTO
}