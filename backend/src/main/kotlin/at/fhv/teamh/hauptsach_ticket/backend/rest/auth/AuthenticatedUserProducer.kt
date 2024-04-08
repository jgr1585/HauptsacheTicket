package at.fhv.teamh.hauptsach_ticket.backend.rest.auth

import at.fhv.teamh.hauptsach_ticket.backend.services.remote.CustomerDBService
import jakarta.enterprise.context.RequestScoped
import jakarta.enterprise.event.Observes
import jakarta.enterprise.inject.Produces

@RequestScoped
open class AuthenticatedUserProducer {

    @Produces
    @RequestScoped
    @AuthenticatedUser
    protected open var authenticatedUser: WebUser? = null

    open fun handleAuthenticationEvent(@Observes @AuthenticatedUser customerId: String?) {
        if (customerId != null) {
            authenticatedUser = WebUser(CustomerDBService.findCustomerById(customerId.toLong()))
        }
    }
}