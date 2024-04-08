package at.fhv.teamh.hauptsach_ticket.library.communication

import java.rmi.Remote
import java.rmi.RemoteException

@Suppress("unused")
interface CustomerDBClientFactory : Remote {

    @Throws(RemoteException::class)
    fun createCustomerDBClient(): CustomerDBClient
}