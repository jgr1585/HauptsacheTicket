package at.fhv.teamh.hauptsach_ticket.backend.rest.controller

import at.fhv.teamh.hauptsach_ticket.backend.application.BeansHandler.injectEJB
import at.fhv.teamh.hauptsach_ticket.backend.rest.exception.TicketNotFoundException
import at.fhv.teamh.hauptsach_ticket.backend.services.EventService
import at.fhv.teamh.hauptsach_ticket.library.dto.EventDTO
import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import jakarta.validation.constraints.PositiveOrZero
import jakarta.ws.rs.*
import java.time.LocalDate

@Path("/event")
@Produces("application/json")
@Consumes("application/json")
@Tag(name = "Event", description = "The API to search for Events")
open class EventController {

    private val eventService: EventService by injectEJB()

    @GET
    @Path("/search")
    @Operation(summary = "Search for tickets", description = "Returns all specified tickets")
    @ApiResponse(
        responseCode = "200", description = "Tickets found",
        content = [Content(array = ArraySchema(schema = Schema(implementation = EventDTO::class)))]
    )
    @ApiResponse(responseCode = "204", description = "Tickets could not be found")
    open fun searchEvents(
        @QueryParam("searchString") @NotNull @Parameter(
            description = "Use this parameter to search for series names or artists",
            examples = [
                ExampleObject(value = "Vorträge des Feilhauers", name = "Example series", description = "Example for a series"),
                ExampleObject(value = "Thomas Feilhauer", name = "Example artist", description = "Example for an artist")
            ]
        ) searchString: String,
        @QueryParam("eventName") @Parameter(
            description = "Use this parameter to search for Event name",
            example = "Frühlings Messe"
        )  eventName: String? = null,
        @QueryParam("genre") @Parameter(
            description = "Use this parameter to search for a specific genre",
            example = "Vortrag"
        ) genre: String? = null,
        @QueryParam("date") @JsonDeserialize(using = LocalDateDeserializer::class) @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd"
        ) @Parameter(
            description = "Use this parameter to search for a Event on a certain date",
            example = "2023-05-13"
        ) date: String? = null,
        @QueryParam("eventNumber") @DefaultValue("0") @PositiveOrZero @Parameter(
            description = "Use this parameter to define the event number where the page starts on"
        ) eventNumber: Int = 0,
        @QueryParam("eventsPerPage") @DefaultValue("25") @Positive @Parameter(
            description = "Use this parameter to define the number of events shown per page"
        ) eventsPerPage: Int = 25,
    ): List<EventDTO> {
        return eventService.searchEvents(
            searchString,
            eventName,
            genre,
            if (date != null) LocalDate.parse(date) else null,
            eventNumber,
            eventsPerPage
        )
            .ifEmpty {
                throw TicketNotFoundException()
            }

    }

    @GET
    @Path("/totalSearch")
    @Operation(summary = " Number of total search for tickets", description = "Returns the number of all specified tickets")
    @ApiResponse(
        responseCode = "200", description = "Total ticket amount found",
        content = [Content(schema = Schema(implementation = Int::class))]
    )
    @ApiResponse(responseCode = "204", description = "Total ticket amount not found")
    open fun totalSearchEvents(
        @QueryParam("searchString") @NotNull @Parameter(
            description = "Use this parameter to search for series names or artists",
            examples = [
                ExampleObject(value = "Vorträge des Feilhauers", name = "Example series", description = "Example for a series"),
                ExampleObject(value = "Thomas Feilhauer", name = "Example artist", description = "Example for an artists")
            ]
        ) searchString: String,
        @QueryParam("eventName") @Parameter(
            description = "Use this parameter to search for Event name",
            example = "Frühlings Messe"
        ) eventName: String? = null,
        @QueryParam("genre") @Parameter(
            description = "Use this parameter to search for a specific genre",
            example = "Vortrag"
        ) genre: String? = null,
        @QueryParam("date") @JsonDeserialize(using = LocalDateDeserializer::class) @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd"
        )@Parameter(
            description = "Use this parameter to search for a Event on a certain date",
            example = "2023-05-13"
        )  date: String? = null,
    ): Int {
        return eventService.totalSearchEvents(
            searchString,
            eventName,
            genre,
            if (date != null) LocalDate.parse(date) else null
        )
    }

    @GET
    @Path("/totalEvents")
    @Operation(summary = "Number of total Events", description = "Total number of events in the database")
    @ApiResponse(
        responseCode = "200", description = "Total event amount found",
        content = [Content(schema = Schema(implementation = Int::class))]
    )
    open fun totalEvents(): Int {
        return eventService.totalEvents()
    }

    @GET
    @Path("/getEvents")
    @Operation(summary = "get Events", description = "Returns the specified number of events")
    @ApiResponse(
        responseCode = "200", description = "Successfully got events",
        content = [Content(array = ArraySchema(schema = Schema(implementation = EventDTO::class)))]
    )
    @ApiResponse(responseCode = "204", description = "Events could not be found")
    open fun getEvents(
        @QueryParam("eventNumber") @DefaultValue("0") @PositiveOrZero @Parameter(
            description = "Use this parameter to define the event number where the page starts on"
        ) eventNumber: Int,
        @QueryParam("eventsPerPage") @DefaultValue("25") @Positive @Parameter(
            description = "Use this parameter to define the number of events shown per page"
        ) eventsPerPage: Int,
    ): List<EventDTO> {
        return eventService.getEvents(eventNumber, eventsPerPage)
    }

}