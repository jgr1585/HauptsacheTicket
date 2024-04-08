package at.fhv.teamh.hauptsach_ticket.backend.application

import jakarta.persistence.EntityManager
import java.io.Closeable

class ClosableEntityManager(private val em: EntityManager) : EntityManager by em, Closeable {

    override fun close() {
        if (em.isOpen) {
            em.close()
        }
    }

    fun delete(entity: Any?) {
        try {
            when (entity) {
                is Iterable<*> -> entity.forEach { delete(it) }
                is Array<*> -> entity.forEach { delete(it) }


                else -> remove(
                    if (contains(entity)) {
                        entity
                    } else {
                        merge(entity)
                    }
                )
            }
        } catch (e: Exception) {
            // ignore
        }
    }

    override fun persist(entity: Any?) {
        try {
            when (entity) {
                is Iterable<*> -> entity.forEach { persist(it) }
                is Array<*> -> entity.forEach { persist(it) }

                else -> {
                    em.persist(entity)
                }
            }
        } catch (e: Exception) {
            // ignore
        }
    }

    fun merge(vararg param: Any) {
        param.forEach {
            merge(it)
        }
    }
}