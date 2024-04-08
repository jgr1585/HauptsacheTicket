package at.fhv.teamh.hauptsach_ticket.backend.services.remote

import at.fhv.teamh.hauptsach_ticket.backend.application.Environment

object MessageService {
    private val env = if (Environment.isDevEnvironment) "dev" else "main"

    val BROKER_URL = "tcp://${Environment.activeMQHost[env]}:${Environment.activeMQPort}"
}