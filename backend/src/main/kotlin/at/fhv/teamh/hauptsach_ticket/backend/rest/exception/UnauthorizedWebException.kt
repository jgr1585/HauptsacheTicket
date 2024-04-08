package at.fhv.teamh.hauptsach_ticket.backend.rest.exception

import at.fhv.teamh.hauptsach_ticket.backend.rest.auth.AuthSave.AUTHENTICATION_SCHEME
import at.fhv.teamh.hauptsach_ticket.backend.rest.auth.AuthSave.REALM
import jakarta.ws.rs.WebApplicationException
import jakarta.ws.rs.core.HttpHeaders
import jakarta.ws.rs.core.Response

class UnauthorizedWebException(
    val msg: String = "Not authorized for functionality",
    val status: Response.Status = Response.Status.UNAUTHORIZED,
) : WebApplicationException(msg, status) {
    init {
        this.response.headers.add(HttpHeaders.WWW_AUTHENTICATE, "$AUTHENTICATION_SCHEME realm=\"${REALM}\"")
    }
}