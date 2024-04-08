package at.fhv.teamh.hauptsach_ticket.customerDB.service

import at.fhv.teamh.hauptsach_ticket.customerDB.application.DtoConverter.toDTO
import at.fhv.teamh.hauptsach_ticket.customerDB.infrastructure.CustomerRepository
import at.fhv.teamh.hauptsach_ticket.library.dto.CustomerDTO

class CustomerService {

    private val customerRepository = CustomerRepository()

    fun findCustomerById(id: Long): CustomerDTO {
        return customerRepository.findCustomerById(id).toDTO()
    }

    fun searchCustomerByName(name: String): List<CustomerDTO> {
        return customerRepository.searchCustomerByName(name).map { it.toDTO() }
    }
}