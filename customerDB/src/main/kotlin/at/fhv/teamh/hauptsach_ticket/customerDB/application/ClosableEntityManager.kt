package at.fhv.teamh.hauptsach_ticket.customerDB.application

import jakarta.persistence.EntityManager
import java.io.Closeable

class ClosableEntityManager(private val em: EntityManager) : EntityManager by em, Closeable {

    override fun close() {
        if (em.isOpen) {
            em.close()
        }
    }
}