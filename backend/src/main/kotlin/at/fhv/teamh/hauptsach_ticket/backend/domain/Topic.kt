package at.fhv.teamh.hauptsach_ticket.backend.domain

import jakarta.jms.Topic

data class Topic(
    val name: String,
) : Comparable<Topic> {
    override fun compareTo(other: Topic) =
        name.compareTo(other.topicName)
}