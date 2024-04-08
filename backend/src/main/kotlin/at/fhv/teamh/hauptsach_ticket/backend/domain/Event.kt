package at.fhv.teamh.hauptsach_ticket.backend.domain

import at.fhv.teamh.hauptsach_ticket.backend.application.DefaultData
import jakarta.persistence.*
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import java.time.LocalDate
import java.util.*

@Entity
@Table(name = "event")
data class Event(
    @Id
    @Column(columnDefinition = "UUID", updatable = false, nullable = false)
    val id: UUID = UUID.randomUUID(),

    @Column(nullable = false)
    val name: String,

    @Column(nullable = false)
    val date: LocalDate,

    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "location", nullable = false)
    val location: Location = DefaultData.location,

    @Column(name = "ticket_reservation", nullable = false)
    val ticketReservation: Boolean = false,

    @Column(nullable = false)
    val genre: String,

    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "series_id", nullable = false)
    val series: Series = DefaultData.series,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Event

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun toString(): String {
        return "Event(id=$id, name='$name', date=$date, location=$location, ticketReservation=$ticketReservation, series=$series, genre=$genre)"
    }
}
