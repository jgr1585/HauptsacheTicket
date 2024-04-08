package at.fhv.teamh.hauptsach.ticket.backend.exception

import jakarta.ws.rs.WebApplicationException
import jakarta.ws.rs.core.Response

class NoVideoException(
    msg: String = "Trailer not found",
    status: Response.Status = Response.Status.NO_CONTENT,
) : WebApplicationException(msg, status)