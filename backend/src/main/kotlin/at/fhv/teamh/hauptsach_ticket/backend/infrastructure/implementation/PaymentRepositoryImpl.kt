package at.fhv.teamh.hauptsach_ticket.backend.infrastructure.implementation

import at.fhv.teamh.hauptsach_ticket.backend.application.DefaultData
import at.fhv.teamh.hauptsach_ticket.backend.application.PersistenceManager
import at.fhv.teamh.hauptsach_ticket.backend.domain.Order
import at.fhv.teamh.hauptsach_ticket.backend.domain.Payment
import at.fhv.teamh.hauptsach_ticket.backend.infrastructure.PaymentRepository
import jakarta.ejb.Local
import jakarta.ejb.Singleton
import java.sql.Date
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

@Local(PaymentRepository::class)
@Singleton
open class PaymentRepositoryImpl : PaymentRepository {
    override fun generatePaymentForOrder(order: Order): Payment {

        val em = PersistenceManager.getEntityManagerInstance()
        val payment = Payment(
            order = order,
            details = "",
            paidOn = Date.valueOf(LocalDate.now()),
            paymentNumber = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)
        )
        em.transaction.begin()
        em.persist(payment)
        em.transaction.commit()
        em.close()

        return payment
    }

    override fun getPaymentByOrderId(orderId: UUID): Payment {
        val em = PersistenceManager.getEntityManagerInstance()
        val paymentQuery = em.createQuery(
            "select p from Payment p where p.order.id = :orderId",
            Payment::class.java
        ).apply {
            setParameter("orderId", orderId)
        }

        return try {
            paymentQuery.singleResult
        } catch (e: Exception) {
            DefaultData.payment
        } finally {
            em.close()
        }
    }
}