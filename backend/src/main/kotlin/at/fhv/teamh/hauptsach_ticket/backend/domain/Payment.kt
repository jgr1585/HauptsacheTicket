package at.fhv.teamh.hauptsach_ticket.backend.domain

import jakarta.persistence.*
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import java.sql.Date
import java.util.*

@Entity
@Table(name = "payment")
data class Payment(
    @Id
    @Column(columnDefinition = "UUID", updatable = false, nullable = false)
    val id: UUID = UUID.randomUUID(),

    @Column(name = "paid_on", nullable = false)
    val paidOn: Date,

    @Column(nullable = false)
    val details: String,

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    val order: Order,

    @Column(nullable = false)
    val paymentNumber: Long,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Payment

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun toString(): String {
        return "Payment(id=$id, paidOn=$paidOn, details='$details', order=$order, paymentNumber=$paymentNumber)"
    }
}
