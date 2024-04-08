package at.fhv.teamh.hauptsach_ticket.backend.rest.controller

import at.fhv.teamh.hauptsach_ticket.backend.application.BeansHandler.injectEJB
import at.fhv.teamh.hauptsach_ticket.backend.services.TicketCategoryService
import at.fhv.teamh.hauptsach_ticket.library.dto.TicketCategoryDTO
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.constraints.NotNull
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import java.util.*

@Path("/ticket")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Ticket", description = "The Api Part controls the tickets")
open class TicketController {

    private val ticketCategoryService: TicketCategoryService by injectEJB()

    @GET
    @Path("/getTicketsForEvent")
    @ApiResponse(
        responseCode = "200", description = "Tickets for event",
        content = [Content(array = ArraySchema(schema = Schema(implementation = TicketCategoryDTO::class)))]
    )
    @ApiResponse(responseCode = "204", description = "Tickets could not be found")
    open fun getTicketsForEvent(
        @QueryParam("eventId") @NotNull @Parameter(
            description = "Use this parameter to search for tickets for a specific event",
        ) eventId: UUID,
    ): List<TicketCategoryDTO> {
        return try {
            ticketCategoryService.findTicketsFromEvent(eventId)
        } catch (e: Exception) {
            throw NotFoundException()
        }
    }
}