package at.fhv.teamh.hauptsach_ticket.backend.rest.auth

import at.fhv.teamh.hauptsach_ticket.backend.application.decorators.DefaultNoArgConstructor
import at.fhv.teamh.hauptsach_ticket.library.dto.CustomerDTO

@DefaultNoArgConstructor
open class WebUser(
    open val customerDTO: CustomerDTO,
)