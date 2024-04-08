package at.fhv.teamh.hauptsach_ticket.customerDB.application

import jakarta.persistence.Persistence

object PersistenceManager {
    private val dbCon = System.getenv("DB_CON") ?: "main"

    private val emf =
        try {
            Persistence.createEntityManagerFactory(dbCon)
        } catch (e: Exception) {
            //Try Dev Connection
            Persistence.createEntityManagerFactory("dev")
                ?: throw IllegalStateException("Could not create EntityManagerFactory")
        }

    fun getEntityManagerInstance() = ClosableEntityManager(
        emf.createEntityManager() ?: throw NullPointerException("Could not create EntityManager")
    )
}