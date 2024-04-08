package at.fhv.teamh.hauptsach_ticket.backend.domain

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "web_user")
data class WebUser(
    @Id
    @Column(name = "id", columnDefinition = "UUID", updatable = false, nullable = false)
    val loginId: UUID = UUID.randomUUID(),

    @Column(nullable = false)
    val password: String,

    @Column(name = "user_state", nullable = false)
    val userState: String,

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "account_id", nullable = false)
    val account: Account,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as WebUser

        return loginId == other.loginId
    }

    override fun hashCode(): Int {
        return loginId.hashCode()
    }

    override fun toString(): String {
        return "WebUser(loginId=$loginId, password='$password', userState='$userState', account=$account)"
    }
}
