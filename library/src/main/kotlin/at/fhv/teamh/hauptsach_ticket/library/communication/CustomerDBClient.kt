package at.fhv.teamh.hauptsach_ticket.library.communication

import at.fhv.teamh.hauptsach_ticket.library.dto.CustomerDTO
import java.rmi.Remote
import java.rmi.RemoteException

@Suppress("unused")
interface CustomerDBClient : Remote {

    @Throws(RemoteException::class)
    fun findCustomerById(id: Long): CustomerDTO

    @Throws(RemoteException::class)
    fun searchCustomerByName(name: String): List<CustomerDTO>
}