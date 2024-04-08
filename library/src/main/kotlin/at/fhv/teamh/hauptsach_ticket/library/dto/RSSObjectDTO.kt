package at.fhv.teamh.hauptsach_ticket.library.dto

import java.io.Serializable

@Suppress("unused")
data class RSSObjectDTO(
    val title: String,
    val description: String,
) : Serializable