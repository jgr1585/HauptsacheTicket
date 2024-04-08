package at.fhv.teamh.hauptsach_ticket.backend.domain

import at.fhv.teamh.hauptsach_ticket.backend.application.DefaultData
import at.fhv.teamh.hauptsach_ticket.library.enums.OrderStatus
import jakarta.persistence.*
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import java.sql.Date
import java.util.*

@Entity
@Table(name = "orders")
data class Order(
    @Id
    @Column(columnDefinition = "UUID", updatable = false, nullable = false)
    val id: UUID = UUID.randomUUID(),

    @Column(name = "order_date", nullable = false)
    val orderDate: Date,

    @Column(name = "billing_address", nullable = false)
    val billingAddress: String,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var status: OrderStatus = OrderStatus.New,

    @OneToMany(mappedBy = "order", cascade = [CascadeType.ALL])
    @OnDelete(action = OnDeleteAction.CASCADE)
    var tickets: MutableList<Ticket> = mutableListOf(),

    @Column(name = "customer_id", nullable = false)
    var customerId: Int = -1,

    @ManyToOne(
        fetch = FetchType.LAZY,
        cascade = [CascadeType.MERGE, CascadeType.REMOVE, CascadeType.REFRESH, CascadeType.DETACH],
        optional = false
    )
    @JoinColumn(name = "account_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    val account: Account = DefaultData.account,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Order

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun toString(): String {
        return "Order(id=$id, orderDate=$orderDate, billingAddress='$billingAddress', status=$status account=$account)"
    }
}