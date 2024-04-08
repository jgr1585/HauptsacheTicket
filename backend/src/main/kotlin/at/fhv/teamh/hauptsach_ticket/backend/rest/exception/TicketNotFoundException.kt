package at.fhv.teamh.hauptsach_ticket.backend.rest.exception

import jakarta.ws.rs.WebApplicationException
import jakarta.ws.rs.core.Response

class TicketNotFoundException : WebApplicationException(
    "Ticket not found",
    Response.Status.NO_CONTENT
)