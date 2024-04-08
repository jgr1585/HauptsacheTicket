package at.fhv.teamh.hauptsach_ticket.customerDB.communication

import at.fhv.teamh.hauptsach_ticket.customerDB.service.CustomerService
import at.fhv.teamh.hauptsach_ticket.library.communication.CustomerDBClient
import at.fhv.teamh.hauptsach_ticket.library.dto.CustomerDTO
import java.rmi.server.UnicastRemoteObject

class CustomerDBClientImpl : CustomerDBClient, UnicastRemoteObject() {

    private val customerService = CustomerService()
    override fun findCustomerById(id: Long): CustomerDTO {
        return customerService.findCustomerById(id)
    }

    override fun searchCustomerByName(name: String): List<CustomerDTO> {
        return customerService.searchCustomerByName(name)
    }
}