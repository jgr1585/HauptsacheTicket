package at.fhv.teamh.hauptsach_ticket.backend.domain

import at.fhv.teamh.hauptsach_ticket.backend.application.DefaultData
import jakarta.persistence.*
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import java.util.*

@Entity
@Table(name = "ticket_number")
class TicketNumber(
    @Id
    @Column(columnDefinition = "UUID", updatable = false, nullable = false)
    val id: UUID = UUID.randomUUID(),

    @Column(nullable = false)
    val number: Int,

    @Column(nullable = false)
    var available: Boolean = true,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "category_id", nullable = false)
    val ticketCategory: TicketCategory = DefaultData.ticketCategory,
) {
    override fun toString(): String {
        return "TicketNumber(id=$id, number=$number, available=$available)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as TicketNumber

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}