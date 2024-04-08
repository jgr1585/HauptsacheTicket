package at.fhv.teamh.hauptsach_ticket.library.dto

import java.io.Serializable
import java.time.LocalDateTime
import java.util.*

data class MessageDTO(
    val id: UUID,
    val title: String,
    val body: String,
    val sendTimestamp: LocalDateTime,
    val topicDTO: TopicDTO,
) : Serializable
