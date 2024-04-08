package at.fhv.teamh.hauptsach_ticket.library.dto

import at.fhv.teamh.hauptsach_ticket.library.enums.OrderStatus
import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer
import java.io.Serializable
import java.sql.Date
import java.util.*

data class OrderDTO(
    val id: UUID,
    @JsonProperty("date")
    @JsonSerialize(using = LocalDateSerializer::class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    val orderDate: Date,
    val billingAddress: String,
    val accountId: UUID,
    val status: OrderStatus,
) : Serializable
