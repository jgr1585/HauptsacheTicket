package at.fhv.teamh.hauptsach_ticket.backend.application

import jakarta.enterprise.inject.spi.CDI


object BeansHandler {
    inline fun <reified T : Any> injectEJB(): Lazy<T> = lazy {
        CDI.current().select(T::class.java).get()
    }
}
