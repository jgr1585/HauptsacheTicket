package at.fhv.teamh.hauptsach_ticket.backend.domain

import at.fhv.teamh.hauptsach_ticket.library.enums.Features
import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "account_permission")
data class AccountPermission(
    @Id
    @Column(columnDefinition = "UUID", updatable = false, nullable = false)
    val id: UUID = UUID.randomUUID(),

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "account_id", nullable = false)
    val account: Account,

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    val permissions: Features,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AccountPermission

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun toString(): String {
        return "AccountPermission(id=$id, account=$account, permissions=$permissions)"
    }
}