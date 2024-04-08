package at.fhv.teamh.hauptsach_ticket.backend.rest.auth

import at.fhv.teamh.hauptsach_ticket.backend.rest.auth.AuthSave.AUTHENTICATION_SCHEME
import at.fhv.teamh.hauptsach_ticket.backend.rest.auth.AuthSave.KEY
import at.fhv.teamh.hauptsach_ticket.backend.rest.exception.UnauthorizedWebException
import io.jsonwebtoken.Jwts
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import jakarta.annotation.Priority
import jakarta.enterprise.event.Event
import jakarta.inject.Inject
import jakarta.ws.rs.Priorities
import jakarta.ws.rs.container.ContainerRequestContext
import jakarta.ws.rs.container.ContainerRequestFilter
import jakarta.ws.rs.core.HttpHeaders
import jakarta.ws.rs.ext.Provider
import java.util.*

@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
@SecurityRequirement(name = "Authentication")
class AuthenticationFilter : ContainerRequestFilter {

    @Inject
    @AuthenticatedUser
    private lateinit var userAuthenticatedEvent: Event<String>

    private val jwtParser = Jwts.parserBuilder().setSigningKey(KEY).build()

    override fun filter(requestContext: ContainerRequestContext) {
        val authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION)

        if (!isTokenBased(authorizationHeader)) {
            throw UnauthorizedWebException()
        }

        val token: String =
            authorizationHeader.substring(AUTHENTICATION_SCHEME.length)
                .trim { it <= ' ' }

        try {
            validateToken(token)
            val username: String = jwtParser
                .parseClaimsJws(token)
                .body
                .subject
            userAuthenticatedEvent.fire(username)
        } catch (e: Exception) {
            throw UnauthorizedWebException()
        }
    }

    private fun isTokenBased(authorizationHeader: String?): Boolean {
        return authorizationHeader?.startsWith("$AUTHENTICATION_SCHEME ", ignoreCase = true) ?: false
    }

    private fun validateToken(token: String) {
        jwtParser
            .parseClaimsJws(token)
    }
}