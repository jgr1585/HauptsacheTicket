package at.fhv.teamh.hauptsach_ticket.backend.rest.exception

import jakarta.ws.rs.WebApplicationException
import jakarta.ws.rs.core.Response

class IncorrectLoginCredentialsException(
    val msg: String = "Invaliad Username or Password",
    val status: Response.Status = Response.Status.UNAUTHORIZED,
) : WebApplicationException(msg, status)