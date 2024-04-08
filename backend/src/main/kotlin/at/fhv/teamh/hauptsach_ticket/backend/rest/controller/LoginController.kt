package at.fhv.teamh.hauptsach_ticket.backend.rest.controller

import at.fhv.teamh.hauptsach_ticket.backend.application.BeansHandler.injectEJB
import at.fhv.teamh.hauptsach_ticket.backend.rest.auth.AuthSave.KEY
import at.fhv.teamh.hauptsach_ticket.backend.rest.auth.AuthenticatedUser
import at.fhv.teamh.hauptsach_ticket.backend.rest.auth.Secured
import at.fhv.teamh.hauptsach_ticket.backend.rest.auth.WebUser
import at.fhv.teamh.hauptsach_ticket.backend.rest.exception.IncorrectLoginCredentialsException
import at.fhv.teamh.hauptsach_ticket.backend.services.remote.AuthLdapService
import io.jsonwebtoken.Jwts
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
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*


@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Login", description = "The API to login to the Hauptsachticket Webpage")
open class LoginController {

    @Inject
    @AuthenticatedUser
    private lateinit var authenticatedUser: WebUser

    private val ldapService: AuthLdapService by injectEJB()

    @POST
    @Path("/login")
    @Operation(summary = "Login", description = "Checks if the credentials are valid and logs the customer in")
    @ApiResponse(
        responseCode = "200", description = "Login Successful",
        content = [Content(schema = Schema(implementation = String::class))]
    )
    @ApiResponse(
        responseCode = "401", description = "Invalid Username or Password",
    )
    @Produces(MediaType.TEXT_PLAIN)
    open fun loginAsCustomer(
        @QueryParam("username") @NotNull @Parameter(
            description = "The Username of the Customer that wants to login",
            example = "Feilhauer123522",
        ) username: String,
        @QueryParam("password") @NotNull @Parameter(
            description = "The Password of the customer account",
            schema = Schema(type = "string", format = "password"),
        ) password: String,
    ): String? {

        if (username.isBlank() || password.isBlank()) throw IncorrectLoginCredentialsException()

        val customerDTO = ldapService.authCustomer(
            username,
            password,
        )
        return customerDTO.id?.let { issueToken(it) } ?: throw IncorrectLoginCredentialsException()
    }

    @POST
    @Path("/logout")
    @Operation(summary = "Logout for the customer", description = "Removes the user from the session")
    @ApiResponse(
        responseCode = "200", description = "Logout successful"
    )
    @ApiResponse(
        responseCode = "401", description = "Not logged in"
    )
    open fun customerLogout(): String? {
        return ""
    }

    @GET
    @Secured
    @SecurityRequirement(name = "Authorization")
    @Path("/getCustomer")
    @Operation(summary = "Get the customer", description = "Returns the customer that is currently logged in")
    @ApiResponse(
        responseCode = "200", description = "Returns the customer that is currently logged in",
        content = [Content(schema = Schema(implementation = Int::class))]
    )
    @ApiResponse(
        responseCode = "401", description = "Not logged in"
    )
    open fun getCustomer(): Int? {
        try {
            return authenticatedUser.customerDTO.id
        } catch (e: Exception) {
            throw IncorrectLoginCredentialsException()
        }
    }

    private fun issueToken(customerId: Int): String? {
        return Jwts.builder()
            .setSubject(customerId.toString())
            .setIssuer("HauptsachTicket")
            .setExpiration(Date.from(LocalDateTime.now().plusHours(24).toInstant(ZoneOffset.UTC)))
            .signWith(KEY)
            .compact()
    }
}