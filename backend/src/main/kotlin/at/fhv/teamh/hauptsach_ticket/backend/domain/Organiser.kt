package at.fhv.teamh.hauptsach_ticket.backend.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.*

@Entity
@Table(name = "organiser")
data class Organiser(
    @Id
    @Column(columnDefinition = "UUID", updatable = false, nullable = false)
    val id: UUID = UUID.randomUUID(),

    @Column(nullable = false)
    val name: String,

    @Column(nullable = false)
    val address: String,

    @Column(nullable = false)
    val eMail: String,

    @Column(nullable = false)
    val telephone: String,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Organiser

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

        override fun toString(): String {
            return "Organiser(id=$id, name='$name', address='$address', eMail='$eMail', telephone='$telephone')"
        }
}