package at.fhv.teamh.hauptsach_ticket.customerDB.infrastructure

import at.fhv.teamh.hauptsach_ticket.customerDB.application.PersistenceManager
import at.fhv.teamh.hauptsach_ticket.customerDB.domain.Customer

class CustomerRepository {

    private val limit = 50

    fun findCustomerById(id: Long): Customer {
        PersistenceManager.getEntityManagerInstance().use { em ->
            val query = em
                .createQuery("SELECT c FROM Customer c WHERE c.id = :id", Customer::class.java)
                .apply {
                    setParameter("id", id)
                }

            return query.singleResult
        }

    }

    fun searchCustomerByName(name: String): List<Customer> {
        PersistenceManager.getEntityManagerInstance().use { em ->
            val query = em
                .createQuery(
                    "SELECT c FROM Customer c WHERE lower(c.familyName) like :name or lower(c.givenName) like :name  or lower(concat( c.familyName, ' ',c.givenName )) like :name",
                    Customer::class.java
                )
                .apply {
                    setParameter("name", "%${name.lowercase()}%")
                    maxResults = limit
                }
            return query.resultList.toList()
        }
    }
}