package at.fhv.teamh.hauptsach_ticket.backend.rest

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType
import io.swagger.v3.oas.annotations.security.SecurityScheme
import io.swagger.v3.oas.annotations.servers.Server
import jakarta.ws.rs.ApplicationPath
import jakarta.ws.rs.core.Application
import openAPIServer


@OpenAPIDefinition(
    info = io.swagger.v3.oas.annotations.info.Info(
        title = "Hauptsach Ticket",
        version = "1.0.0",
        description = "Hauptsach Ticket API"
    ),
    servers = [
        Server(
            description = "Server",
            url = openAPIServer
        )
    ]
)
@SecurityScheme(
    name = "Authorization",
    type = SecuritySchemeType.HTTP,
    scheme = "bearer",
    bearerFormat = "JWT",
    `in` = SecuritySchemeIn.HEADER
)
@ApplicationPath("/api")
open class RestConfig : Application()