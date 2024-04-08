package at.fhv.teamh.hauptsach_ticket.library.dto

import java.io.Serializable
import java.util.*

data class TicketDTO(
    val id: UUID,
    val ticketNumber: Int,
    val ticketCategory: TicketCategoryDTO,
) : Serializable
