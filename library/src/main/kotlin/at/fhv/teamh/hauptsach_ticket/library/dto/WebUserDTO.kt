package at.fhv.teamh.hauptsach_ticket.library.dto

import java.io.Serializable
import java.util.*

@Suppress("unused")
data class WebUserDTO(
    val id: UUID,
    val password: String,
    val userState: String,
) : Serializable
