package at.fhv.teamh.hauptsach_ticket.backend.domain

import at.fhv.teamh.hauptsach_ticket.backend.application.DefaultData
import jakarta.persistence.*
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import java.util.*

@Entity
@Table(name = "ticket")
data class Ticket(
    @Id
    @Column(columnDefinition = "UUID", updatable = false, nullable = false)
    val id: UUID = UUID.randomUUID(),

    @Column(name = "ticket_number", nullable = false)
    val ticketNumber: Int,

    @ManyToOne(
        fetch = FetchType.LAZY,
        cascade = [CascadeType.MERGE, CascadeType.REMOVE, CascadeType.REFRESH, CascadeType.DETACH],
        optional = false
    )
    @JoinColumn(name = "ticket_category_id", nullable = false)
    val ticketCategory: TicketCategory,

    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "order_id", nullable = false)
    var order: Order = DefaultData.order,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Ticket

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun toString(): String {
        return "Ticket(id=$id, ticketNumber=$ticketNumber, ticketCategory=$ticketCategory, order=$order)"
    }
}
