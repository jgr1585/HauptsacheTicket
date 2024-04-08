package at.fhv.teamh.hauptsach_ticket.library.dto

import java.io.Serializable
import java.sql.Date
import java.util.*

@Suppress("unused")
data class PaymentDTO(
    val id: UUID,
    val paidOn: Date,
    val details: String,
    val orderId: UUID,
    val paymentNumber: Long,
) : Serializable
