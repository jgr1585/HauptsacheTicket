package at.fhv.teamh.hauptsach_ticket.backend.rest.json

import at.fhv.teamh.hauptsach_ticket.backend.application.decorators.DefaultNoArgConstructor
import jakarta.annotation.Nullable
import jakarta.validation.constraints.Positive
import org.jetbrains.annotations.NotNull
import java.io.Serializable
import java.util.*

@DefaultNoArgConstructor
data class TicketNumberAndCategory(
    @Positive @Nullable
    val ticketNumber: Int?,
    @NotNull
    val ticketCategoryId: UUID,
) : Serializable