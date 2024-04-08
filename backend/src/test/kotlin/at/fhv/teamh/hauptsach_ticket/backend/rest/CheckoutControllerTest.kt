package at.fhv.teamh.hauptsach_ticket.backend.rest

import at.fhv.teamh.hauptsach_ticket.backend.application.BeansHandler
import at.fhv.teamh.hauptsach_ticket.backend.application.DtoConverter.toDto
import at.fhv.teamh.hauptsach_ticket.backend.domain.DomainFactory
import at.fhv.teamh.hauptsach_ticket.backend.domain.TicketNumber
import at.fhv.teamh.hauptsach_ticket.backend.infrastructure.TicketCategoryRepository
import at.fhv.teamh.hauptsach_ticket.backend.rest.auth.WebUser
import at.fhv.teamh.hauptsach_ticket.backend.rest.controller.CheckoutController
import at.fhv.teamh.hauptsach_ticket.backend.rest.json.TicketNumberAndCategory
import at.fhv.teamh.hauptsach_ticket.backend.services.OrderService
import at.fhv.teamh.hauptsach_ticket.backend.services.TicketService
import at.fhv.teamh.hauptsach_ticket.library.dto.CustomerDTO
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import java.util.*
import kotlin.test.assertEquals

class CheckoutControllerTest {
    private val orderService: OrderService by BeansHandler.injectEJB()
    private val ticketService: TicketService by BeansHandler.injectEJB()
    private val ticketCategoryRepository: TicketCategoryRepository by BeansHandler.injectEJB()
    private val checkoutController = CheckoutController()

    @Test
    fun `GIVEN threeTickets WHEN checkoutFromCart THEN returnOrder`() {
        val customerId = 1
        val ticketCategoryId1 = UUID.randomUUID()
        val ticketCategoryId2 = UUID.randomUUID()
        val ticketCategoryId3 = UUID.randomUUID()
        val ticketNumbersAndCategory = listOf(
            TicketNumberAndCategory(1, ticketCategoryId1),
            TicketNumberAndCategory(2, ticketCategoryId2),
            TicketNumberAndCategory(3, ticketCategoryId3),
        )
        val tickets = with (DomainFactory) {
            listOf(
                createTicket().toDto(),
                createTicket().toDto(),
                createTicket().toDto(),
            )
        }
        val orderExpected = with (DomainFactory) {
            createOrder().toDto()
        }

        val clazz = checkoutController::class.java
        with(clazz.getDeclaredField("authenticatedUser")) {
            isAccessible = true
            set(checkoutController, WebUser(
                CustomerDTO(
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
                    null,
                    null,
                    null,
                    null,
                )
            )
            )
        }

        `when`(ticketService.addTicketsToCart(ticketCategoryId1, 1, listOf(1))).thenReturn(listOf(tickets[0]))
        `when`(ticketService.addTicketsToCart(ticketCategoryId2, 1, listOf(2))).thenReturn(listOf(tickets[1]))
        `when`(ticketService.addTicketsToCart(ticketCategoryId3, 1, listOf(3))).thenReturn(listOf(tickets[2]))

        `when`(ticketCategoryRepository.getTicketNumbers(ticketCategoryId1)).thenReturn(listOf(TicketNumber(number = 1)))
        `when`(ticketCategoryRepository.getTicketNumbers(ticketCategoryId2)).thenReturn(listOf(TicketNumber(number = 2)))
        `when`(ticketCategoryRepository.getTicketNumbers(ticketCategoryId3)).thenReturn(listOf(TicketNumber(number = 3)))

        `when`(orderService.createOrderFromCart(tickets.map { it.id }, customerId)).thenReturn(orderExpected)

        //WHEN
        val orderActual = checkoutController.checkoutFromCart(ticketNumbersAndCategory)

        //THEN
        assertEquals(orderExpected, orderActual)
    }
}