package at.fhv.teamh.hauptsach_ticket.frontend.data

import at.fhv.teamh.hauptsach_ticket.library.dto.CustomerDTO
import javafx.beans.property.SimpleStringProperty

class Customer {
    var customerDTO: CustomerDTO? = null
        set(value) {
            field = value

            if (value != null) {
                customerId.value = value.id.toString()
                customerName.value = "${value.givenName} ${value.familyName}"
                customerAddress.value = with(value) {
                    "$addressStreetAddress $addressHouseNumber, $addressPostalCode $addressAddressLocality"
                }

            } else {
                customerId.value = ""
                customerName.value = ""
                customerAddress.value = ""
            }
        }

    val customerId = SimpleStringProperty("")
    val customerName = SimpleStringProperty("")
    val customerAddress = SimpleStringProperty("")
}