package at.fhv.teamh.hauptsach.ticket.backend

import at.fhv.teamh.hauptsach.ticket.backend.domain.Customer
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import java.util.*

@Path("/wishlist")
class WishlistResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun getWishlistForUser(
        @QueryParam("userId") userId: Int,
    ): Set<String> {
        return Customer
            .find("customerId", userId)
            .firstResult()
            ?.wishList
            ?: emptySet()
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    fun addTicketToWishlist(
        @QueryParam("userId") userId: Int,
        @QueryParam("ticketId") ticketId: String,
    ): Set<String> {
        val customer = Customer
            .find("customerId", userId)
            .firstResult()
            ?: Customer(userId)

        customer.wishList.add(ticketId)
        customer.persistOrUpdate()

        return customer.wishList
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    fun deleteTicketFromWishlist(
        @QueryParam("userId") userId: Int,
        @QueryParam("ticketId") ticketId: String,
    ): Boolean {
        val customer = Customer
            .find("customerId", userId)
            .firstResult()
            ?: return false

        customer.wishList.remove(ticketId)
        customer.update()

        return true
    }
}