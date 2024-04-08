package at.fhv.teamh.hauptsach_ticket.library.communication

import at.fhv.teamh.hauptsach_ticket.library.dto.MessageDTO
import java.rmi.Remote
import java.rmi.RemoteException

@Suppress("unused")
interface MessageSubscriber : Remote {

    @get:Throws(RemoteException::class)
    val client: ApplicationClient

    @Throws(RemoteException::class)
    fun newMessage(messageDTO: MessageDTO)

    @Throws(RemoteException::class)
    fun acknowledgeMessage(messageDTO: MessageDTO)
}