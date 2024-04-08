package at.fhv.teamh.hauptsach_ticket.backend.rest.controller

import at.fhv.teamh.hauptsach_ticket.backend.application.BeansHandler.injectEJB
import at.fhv.teamh.hauptsach_ticket.backend.infrastructure.TicketCategoryRepository
import at.fhv.teamh.hauptsach_ticket.backend.rest.auth.AuthenticatedUser
import at.fhv.teamh.hauptsach_ticket.backend.rest.auth.Secured
import at.fhv.teamh.hauptsach_ticket.backend.rest.auth.WebUser
import at.fhv.teamh.hauptsach_ticket.backend.rest.exception.UnauthorizedWebException
import at.fhv.teamh.hauptsach_ticket.backend.rest.json.TicketNumberAndCategory
import at.fhv.teamh.hauptsach_ticket.backend.services.OrderService
import at.fhv.teamh.hauptsach_ticket.backend.services.TicketService
import at.fhv.teamh.hauptsach_ticket.library.dto.OrderDTO
import at.fhv.teamh.hauptsach_ticket.library.dto.TicketDTO
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import org.jetbrains.annotations.NotNull
import java.util.*

@Secured
@SecurityRequirement(name = "Authorization")
@Path("/checkout")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Checkout", description = "The Api Part that allows to customer to checkout their tickets")
open class CheckoutController {

    private val orderService: OrderService by injectEJB()
    private val ticketService: TicketService by injectEJB()
    private val ticketCategoryRepository: TicketCategoryRepository by injectEJB()

    @Inject
    @AuthenticatedUser
    private lateinit var authenticatedUser: WebUser

    @POST
    @Operation(
        summary = "checkout",
        description = "Takes the tickets from the shopping cart and creates an order with them"
    )
    @ApiResponse(
        responseCode = "201", description = "Checkout Successful",
        content = [Content(schema = Schema(implementation = OrderDTO::class))]
    )
    @ApiResponse(
        responseCode = "404", description = "These tickets are not available",
    )
    open fun checkoutFromCart(
        @NotNull @Parameter(
            description = "The tickets to checkout",
            required = true,
        ) tickets: List<TicketNumberAndCategory>,
    ): OrderDTO {
        val ticketDTOs = mutableListOf<TicketDTO>()
        tickets.forEach {
            ticketDTOs.addAll(
                ticketService.addTicketsToCart(
                    ticketNumbers = listOf(
                        it.ticketNumber ?: ticketCategoryRepository.getTicketNumbers(it.ticketCategoryId).first().number
                    ),
                    amount = 1,
                    ticketCategoryId = it.ticketCategoryId,
                )
            )
        }

        if (ticketDTOs.isEmpty()) throw NotFoundException()

        return orderService.createOrderFromCart(
            ticketIds = ticketDTOs.map { it.id },
            customerId = authenticatedUser.customerDTO.id ?: throw UnauthorizedWebException()
        )
    }
}