package at.fhv.teamh.hauptsach_ticket.library.dto

import java.io.Serializable
import java.util.*

data class SeriesDTO(
    val id: UUID,
    val name: String,
    val artist: String,
) : Serializable
