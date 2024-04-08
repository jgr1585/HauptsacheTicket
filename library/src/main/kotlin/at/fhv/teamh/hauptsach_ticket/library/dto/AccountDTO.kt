package at.fhv.teamh.hauptsach_ticket.library.dto

import java.io.Serializable
import java.util.*

@Suppress("unused")
data class AccountDTO(
    val id: UUID,
    val customerId: Int,
) : Serializable
