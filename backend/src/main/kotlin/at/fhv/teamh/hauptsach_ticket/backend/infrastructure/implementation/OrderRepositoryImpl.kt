package at.fhv.teamh.hauptsach_ticket.backend.infrastructure.implementation

import at.fhv.teamh.hauptsach_ticket.backend.application.DefaultData
import at.fhv.teamh.hauptsach_ticket.backend.application.PersistenceManager
import at.fhv.teamh.hauptsach_ticket.backend.domain.Order
import at.fhv.teamh.hauptsach_ticket.backend.domain.Ticket
import at.fhv.teamh.hauptsach_ticket.backend.infrastructure.OrderRepository
import at.fhv.teamh.hauptsach_ticket.library.enums.OrderStatus
import jakarta.ejb.Local
import jakarta.ejb.Singleton
import jakarta.persistence.NoResultException
import java.sql.Date
import java.time.LocalDate
import java.util.*

@Local(OrderRepository::class)
@Singleton
open class OrderRepositoryImpl : OrderRepository {
    override fun createOrder(customerId: Int?): Order {
        val em = PersistenceManager.getEntityManagerInstance()

        val order = Order(
            orderDate = Date.valueOf(LocalDate.now()),
            account = DefaultData.account,
            billingAddress = "(default)",
            customerId = customerId ?: -1,
        )

        em.transaction.begin()
        em.persist(order)
        em.transaction.commit()
        em.close()

        return order
    }

    override fun getOrders(): List<Order> {
        val em = PersistenceManager.getEntityManagerInstance()
        val query = em.createQuery(
            "select o from Order o order by orderDate desc",
            Order::class.java
        ).apply {
            maxResults = 25
        }

        return em.use {
            query.resultList.toList()
        }
    }

    override fun getOrderById(orderId: UUID): Order {
        val em = PersistenceManager.getEntityManagerInstance()
        val orderQuery = em.createQuery(
            "select o from Order o where o.id = :orderId",
            Order::class.java
        ).apply {
            setParameter("orderId", orderId)
        }

        return try {
            orderQuery.singleResult
        } catch (e: Exception) {
            DefaultData.order
        } finally {
            em.close()
        }
    }

    override fun addTicketsToOrder(ticketIds: List<UUID>, orderId: UUID) {
        val em = PersistenceManager.getEntityManagerInstance()
        var order = getOrderById(orderId)

        if (order == DefaultData.order) {
            order = createOrder()
        }

        val tickets = getTicketsFromId(ticketIds)

        em.transaction.begin()

        tickets.forEach {
            it.order = order
            em.merge(it)
        }

        em.transaction.commit()
        em.close()
    }

    override fun addCustomerToOrder(orderId: UUID, customerId: Int) {
        val em = PersistenceManager.getEntityManagerInstance()
        val order = getOrderById(orderId)

        em.transaction.begin()

        order.customerId = customerId
        em.merge(order)
        em.flush()
        em.transaction.commit()
        em.close()
    }

    override fun getOrdersByCustomerId(id: Int): List<Order> {
        val em = PersistenceManager.getEntityManagerInstance()
        val orderQuery = em.createQuery(
            "select o from Order o where o.customerId = :customerId",
            Order::class.java
        ).apply {
            setParameter("customerId", id)
        }

        return em.use {
            orderQuery.resultList.toList()
        }
    }

    override fun markOrderAsCanceled(id: UUID): Order {
        val em = PersistenceManager.getEntityManagerInstance()
        val order = getOrderById(id)

        order.status = OrderStatus.Canceled

        em.transaction.begin()
        em.merge(order)
        em.transaction.commit()
        em.close()

        return order
    }

    override fun closeOrder(id: UUID) {
        val em = PersistenceManager.getEntityManagerInstance()

        val order = getOrderById(id)
        order.status = OrderStatus.Closed

        em.transaction.begin()
        em.merge(order)
        em.transaction.commit()
        em.close()
    }

    private fun getTicketsFromId(ticketIds: List<UUID>): List<Ticket> {
        return PersistenceManager.getEntityManagerInstance().use { em ->
            ticketIds.map {

                val ticketQuery = em.createQuery(
                    "select t from Ticket t where t.id = :ticketId",
                    Ticket::class.java
                ).apply {
                    setParameter("ticketId", it)
                }

                try {
                    ticketQuery.singleResult
                } catch (e: NoResultException) {
                    DefaultData.ticket
                }
            }
        }
    }
}
