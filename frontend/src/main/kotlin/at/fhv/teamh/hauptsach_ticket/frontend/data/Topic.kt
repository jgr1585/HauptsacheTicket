package at.fhv.teamh.hauptsach_ticket.frontend.data

import javax.jms.Topic

data class Topic(
    val name: String,
) : Topic, Comparable<Topic> {
    override fun compareTo(other: Topic) =
        name.compareTo(other.topicName)

    override fun getTopicName() = name
}