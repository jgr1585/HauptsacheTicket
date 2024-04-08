package at.fhv.teamh.hauptsach_ticket.frontend.communication

import at.fhv.teamh.hauptsach_ticket.library.communication.ApplicationClient
import at.fhv.teamh.hauptsach_ticket.library.dto.*
import at.fhv.teamh.hauptsach_ticket.library.enums.Permission
import at.fhv.teamh.hauptsach_ticket.library.exception.NoPermissionException
import java.time.LocalDate
import java.util.*
import javax.naming.Context
import javax.naming.InitialContext


object RemoteFacade : ApplicationClient {
    private var applicationClient: ApplicationClient? = null
    private var messageService: MessageService? = null

    private const val errorMessage = "No Permission"

    val permissions: List<Permission>
        get() = ldapUser.permissions

    override val ldapUser: LdapUserDTO
        get() = applicationClient?.ldapUser ?: LdapUserDTO(
            id = UUID(0, 0),
            username = "",
            permissions = emptyList(),
            subscribedTopics = emptyList(),
            allTopics = emptyList()
        )

    override val shoppingCart: List<TicketDTO>
        get() = applicationClient?.shoppingCart ?: emptyList()

    @Suppress("unused")
    val isConnected: Boolean
        get() = applicationClient != null

    fun connect(host: String, user: String, password: String): Boolean {
        return try {
            val props = Properties()
            props[Context.INITIAL_CONTEXT_FACTORY] = "org.wildfly.naming.client.WildFlyInitialContextFactory"
            props[Context.PROVIDER_URL] = "http-remoting://$host:8080"
            val ctx = InitialContext(props)

            applicationClient =
                ctx.lookup("ejb:/Backend-1.0-SNAPSHOT/ApplicationClientImpl!at.fhv.teamh.hauptsach_ticket.library.communication.ApplicationClient?stateful") as ApplicationClient


            applicationClient!!.authenticate(user, password)

            messageService = MessageService(ldapUser)
            messageService!!.subscribe()

            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    override fun searchOrderById(id: UUID): OrderDTO {
        try {
            return applicationClient?.searchOrderById(id)
                ?: throw notConnectedException()
        } catch (e: NoPermissionException) {
            error(errorMessage)
        }
    }

    override fun markOrderAsCanceled(id: UUID) {
        try {
            return applicationClient?.markOrderAsCanceled(id)
                ?: throw notConnectedException()
        } catch (e: NoPermissionException) {
            error(errorMessage)
        }
    }

    override fun getOrdersByCustomerId(customerId: Int): List<OrderDTO> {
        return applicationClient?.getOrdersByCustomerId(customerId)
            ?: throw notConnectedException()
    }

    override fun getPaymentByOrderId(orderId: UUID): PaymentDTO {
        return applicationClient?.getPaymentByOrderId(orderId)
            ?: throw notConnectedException()
    }

    fun disconnect() {

        try {
            applicationClient?.logout()
        } catch (e: Exception) {
            // Ignore
        }

        applicationClient = null

        messageService?.close()
        messageService = null
    }

    fun getMessageService(): MessageService {

        if (messageService == null) {
            while (applicationClient == null) {
                Thread.sleep(100)
            }

            messageService = MessageService(ldapUser)
        }

        return messageService!!
    }

    private fun notConnectedException() =
        IllegalStateException("Not connected to server")


    override fun getEvents(eventNumber: Int, eventsPerPage: Int): List<EventDTO> {
        return applicationClient?.getEvents(eventNumber, eventsPerPage)
            ?: throw notConnectedException()
    }

    override fun getTicketsForEvent(eventId: UUID): List<TicketCategoryDTO> {
        return applicationClient?.getTicketsForEvent(eventId)
            ?: throw notConnectedException()
    }

    override fun removeTicketsFromCart(ticketIds: List<UUID>) {
        try {
            return applicationClient?.removeTicketsFromCart(ticketIds)
                ?: throw notConnectedException()
        } catch (e: NoPermissionException) {
            error(errorMessage)
        }
    }

    override fun searchCustomerByName(name: String): List<CustomerDTO> {
        try {
            return applicationClient?.searchCustomerByName(name)
                ?: throw notConnectedException()
        } catch (e: NoPermissionException) {
            error(errorMessage)
        }
    }

    override fun totalEvents(): Int {
        return applicationClient?.totalEvents()
            ?: throw notConnectedException()
    }

    override fun searchEvents(
        searchString: String,
        eventName: String?,
        genre: String?,
        date: LocalDate?,
        eventNumber: Int,
        eventsPerPage: Int,
    ): List<EventDTO> {
        try {
            return applicationClient?.searchEvents(searchString, eventName, genre, date, eventNumber, eventsPerPage)
                ?: throw notConnectedException()
        } catch (e: NoPermissionException) {
            error(errorMessage)
        }
    }

    override fun totalSearchEvents(searchString: String, eventName: String?, genre: String?, date: LocalDate?): Int {
        return applicationClient?.totalSearchEvents(searchString, eventName, genre, date)
            ?: throw notConnectedException()
    }

    override fun addTicketsToCart(ticketCategoryId: UUID, amount: Int, ticketNumbers: List<Int>): List<TicketDTO> {
        try {
            return applicationClient?.addTicketsToCart(ticketCategoryId, amount, ticketNumbers)
                ?: throw notConnectedException()
        } catch (e: NoPermissionException) {
            error(errorMessage)
        }
    }

    override fun authenticate(username: String, password: String): Boolean {
        throw UnsupportedOperationException("Not supported")
    }

    override fun checkoutFromCart(ticketIds: List<UUID>, customerId: Int) {
        return applicationClient?.checkoutFromCart(ticketIds, customerId)
            ?: throw notConnectedException()
    }

    override fun findEventGenre(genre: String): List<EventDTO> {
        return applicationClient?.findEventGenre(genre)
            ?: throw notConnectedException()
    }

    override fun findEventName(eventName: String): List<EventDTO> {
        return applicationClient?.findEventName(eventName)
            ?: throw notConnectedException()
    }

    override fun getActiveMQBroker(): String {
        return applicationClient?.getActiveMQBroker()
            ?: throw notConnectedException()
    }

    override fun getAvailableTicketNumbers(ticketCategoryId: UUID): List<Int> {
        try {
            return applicationClient?.getAvailableTicketNumbers(ticketCategoryId)
                ?: throw notConnectedException()
        } catch (e: NoPermissionException) {
            error(errorMessage)
        }
    }

    override fun getEventByCategoryId(ticketCategoryId: UUID): EventDTO {
        return applicationClient?.getEventByCategoryId(ticketCategoryId)
            ?: throw notConnectedException()
    }

    override fun logout() {
        disconnect()
    }
}