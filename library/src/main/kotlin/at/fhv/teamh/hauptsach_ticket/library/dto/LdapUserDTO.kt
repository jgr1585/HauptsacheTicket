package at.fhv.teamh.hauptsach_ticket.library.dto

import at.fhv.teamh.hauptsach_ticket.library.enums.Permission
import java.io.Serializable
import java.util.*

data class LdapUserDTO(
    val id: UUID,
    val username: String,
    val permissions: List<Permission>,
    val subscribedTopics: List<TopicDTO>,
    val allTopics: List<TopicDTO>,
) : Serializable
