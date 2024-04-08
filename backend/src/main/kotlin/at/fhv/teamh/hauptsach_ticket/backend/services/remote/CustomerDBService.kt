package at.fhv.teamh.hauptsach_ticket.backend.services.remote

import at.fhv.teamh.hauptsach_ticket.backend.application.Environment
import at.fhv.teamh.hauptsach_ticket.library.communication.CustomerDBClient
import at.fhv.teamh.hauptsach_ticket.library.communication.CustomerDBClientFactory
import java.rmi.Naming

object CustomerDBService: CustomerDBClient by getCustomerDBClient()

private fun getCustomerDBClient(): CustomerDBClient {
    val env = if (Environment.isDevEnvironment) "dev" else "main"

    val customerDBClientFactory =
        Naming.lookup("rmi://${Environment.customerDBClientHost[env]}:${Environment.customerDBClientPort}/CustomerDBClientFactory") as CustomerDBClientFactory

    return customerDBClientFactory.createCustomerDBClient()
}