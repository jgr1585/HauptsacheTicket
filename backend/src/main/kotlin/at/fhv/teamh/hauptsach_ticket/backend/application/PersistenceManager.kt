package at.fhv.teamh.hauptsach_ticket.backend.application

import jakarta.persistence.Persistence

object PersistenceManager {
    private val dbCon = if (Environment.isDevEnvironment) "dev" else "main"

    private val emf =
        try {
            Persistence.createEntityManagerFactory("dev")
        } catch (e: Exception) {
            e.printStackTrace()
            Persistence.createEntityManagerFactory(dbCon)
                ?: throw IllegalStateException("Could not create EntityManagerFactory")
        }

    fun getEntityManagerInstance() = ClosableEntityManager(
        emf.createEntityManager() ?: throw NullPointerException("Could not create EntityManager")
    )
}