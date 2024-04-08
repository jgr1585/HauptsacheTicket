package at.fhv.teamh.hauptsach_ticket.customerDB.application

import at.fhv.teamh.hauptsach_ticket.customerDB.domain.Customer
import at.fhv.teamh.hauptsach_ticket.library.dto.CustomerDTO

object DtoConverter {
    fun Customer.toDTO() =
        CustomerDTO(
            id = this.id,
            givenName = this.givenName,
            familyName = this.familyName,
            gender = this.gender,
            birthDate = this.birthDate,
            height = this.height,
            eyecolor = this.eyecolor,
            email = this.email,
            taxId = this.taxId,
            addressAddressCountry = this.addressAddressCountry,
            addressAddressLocality = this.addressAddressLocality,
            addressPostalCode = this.addressPostalCode,
            addressStreetAddress = this.addressStreetAddress,
            addressHouseNumber = this.addressHouseNumber,
            phoneNo = this.phoneNo,
            mobileNo = this.mobileNo,
            bankAccountBankCity = this.bankAccountBankCity,
            bankAccountBankBankCode = this.bankAccountBankBankCode,
            bankAccountBankDesc = this.bankAccountBankDesc,
            bankAccountBankBic = this.bankAccountBankBic,
            bankAccountIban = this.bankAccountIban,
            creditCardNumber = this.creditCardNumber,
            creditCardType = this.creditCardType,
            creditCardCvc = this.creditCardCvc,
            birthName = this.birthName,
        )
}