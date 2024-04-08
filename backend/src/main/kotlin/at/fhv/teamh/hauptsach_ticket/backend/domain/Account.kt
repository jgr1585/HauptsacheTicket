package at.fhv.teamh.hauptsach_ticket.backend.domain

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "accounts")
data class Account(
    @Id
    @Column(columnDefinition = "UUID", updatable = false, nullable = false)
    val id: UUID = UUID.randomUUID(),

    @Column(name = "customer_id", nullable = false)
    val customerId: Int,

    @OneToMany(
        mappedBy = "account",
        cascade = [CascadeType.MERGE, CascadeType.REMOVE, CascadeType.REFRESH, CascadeType.DETACH]
    )
    val orders: MutableList<Order> = mutableListOf(),

    @OneToMany(cascade = [(CascadeType.ALL)], fetch = FetchType.LAZY, mappedBy = "account")
    val permissions: List<AccountPermission> = listOf(),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Account

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun toString(): String {
        return "Account(id=$id, customerId=$customerId)"
    }
}
