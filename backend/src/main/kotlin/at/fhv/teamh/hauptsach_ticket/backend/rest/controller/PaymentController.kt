package at.fhv.teamh.hauptsach_ticket.backend.rest.controller

import at.fhv.teamh.hauptsach_ticket.backend.application.BeansHandler.injectEJB
import at.fhv.teamh.hauptsach_ticket.backend.domain.Order
import at.fhv.teamh.hauptsach_ticket.backend.rest.auth.AuthenticatedUser
import at.fhv.teamh.hauptsach_ticket.backend.rest.auth.Secured
import at.fhv.teamh.hauptsach_ticket.backend.rest.auth.WebUser
import at.fhv.teamh.hauptsach_ticket.backend.services.OrderService
import at.fhv.teamh.hauptsach_ticket.backend.services.PaymentService
import at.fhv.teamh.hauptsach_ticket.library.dto.PaymentDTO
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
@Path("/payment")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Payment", description = "API endpoints for payment processing")
open class PaymentController {

    @Inject
    @AuthenticatedUser
    private lateinit var authenticatedUser: WebUser

    private val paymentService: PaymentService by injectEJB()
    private val orderService: OrderService by injectEJB()

    @POST
    @Path("/generate")
    @Operation(summary = "Generate a payment for an order")
    @ApiResponse(
        responseCode = "200",
        description = "Payment generated successfully",
        content = [Content(schema = Schema(implementation = PaymentDTO::class))]
    )
    open fun generatePaymentForOrder(
        @NotNull @Parameter(description = "The order to generate payment for") order: Order,
    ): PaymentDTO {
        return paymentService.generatePaymentForOrder(order)
    }

    @GET
    @Path("/order/{id}")
    @Operation(summary = "Get payment by order ID")
    @ApiResponse(
        responseCode = "200",
        description = "Payment retrieved successfully",
        content = [Content(schema = Schema(implementation = PaymentDTO::class))]
    )
    @ApiResponse(responseCode = "404", description = "Payment not found")
    open fun getPaymentByOrderId(
        @PathParam("id") id: UUID,
    ): PaymentDTO {
        return paymentService.getPaymentByOrderId(id)
    }

    @POST
    @Path("/pay")
    @Operation(summary = "Make payment as a customer")
    @ApiResponse(
        responseCode = "200",
        description = "Payment processed successfully",
        content = [Content(schema = Schema(implementation = PaymentDTO::class))]
    )
    open fun payAsCustomer(
        @NotNull @Parameter(description = "Card Number") @QueryParam("cardNumber") cardNumber: Long,
        @NotNull @Parameter(description = "Order ID") @QueryParam("orderId") orderId: UUID,
    ): PaymentDTO {
        val order = orderService.searchOrderById(orderId)
        return paymentService.payAsCustomer(authenticatedUser.customerDTO.id!!, cardNumber, order)
    }
}



