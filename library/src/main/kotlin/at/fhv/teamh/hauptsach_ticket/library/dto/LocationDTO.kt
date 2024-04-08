package at.fhv.teamh.hauptsach_ticket.library.dto

import java.io.Serializable
import java.util.*

data class LocationDTO(
    val id: UUID,
    val address: String,
    val building: String,
    val room: String,
) : Serializable
