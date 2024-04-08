package at.fhv.teamh.hauptsach_ticket.library.dto

import at.fhv.teamh.hauptsach_ticket.library.enums.Features
import java.io.Serializable
import java.util.*

@Suppress("unused")
data class AccountPermissionDTO(
    val id: UUID,
    val permissions: Features
) : Serializable
