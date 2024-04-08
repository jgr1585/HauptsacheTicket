package at.fhv.teamh.hauptsach_ticket.backend.rest.auth

import jakarta.ws.rs.NameBinding


@NameBinding
@Retention(AnnotationRetention.RUNTIME)
@Target(
    AnnotationTarget.CLASS,
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
annotation class Secured