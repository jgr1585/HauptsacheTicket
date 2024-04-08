package at.fhv.teamh.hauptsach_ticket.library.dto

import java.io.Serializable
import java.util.*

data class TicketCategoryDTO(
    val id: UUID,
    val name: String,
    val price: Double,
    val totalTickets: Int,
    val remainingTickets: Int,
    val availableTicketNumbers: List<Int>
) : Serializable
