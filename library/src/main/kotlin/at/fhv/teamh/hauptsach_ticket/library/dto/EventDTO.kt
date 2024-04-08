package at.fhv.teamh.hauptsach_ticket.library.dto

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer
import java.io.Serializable
import java.time.LocalDate
import java.util.*

data class EventDTO(
    val id: UUID,
    val name: String,
    @JsonProperty("date")
    @JsonSerialize(using = LocalDateSerializer::class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    val date: LocalDate,
    val ticketReservation: Boolean,
    val series: SeriesDTO,
    val location: LocationDTO,
    val genre: String,
) : Serializable
