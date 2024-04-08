package at.fhv.teamh.hauptsach_ticket.customerDB.communication

import at.fhv.teamh.hauptsach_ticket.library.communication.CustomerDBClient
import at.fhv.teamh.hauptsach_ticket.library.communication.CustomerDBClientFactory
import java.rmi.server.UnicastRemoteObject

class CustomerDBClientFactoryImpl : CustomerDBClientFactory, UnicastRemoteObject() {
    override fun createCustomerDBClient(): CustomerDBClient {
        return CustomerDBClientImpl()
    }
}