package at.fhv.teamh.hauptsach_ticket.library.dto

import java.io.Serializable
import java.util.*

@Suppress("unused")
data class OrganiserDTO(
    val id: UUID,
    val name: String,
    val address: String,
    val eMail: String,
    val telephone: String,
) : Serializable
