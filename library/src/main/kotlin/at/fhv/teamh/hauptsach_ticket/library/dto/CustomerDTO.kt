package at.fhv.teamh.hauptsach_ticket.library.dto

import java.io.Serializable

data class CustomerDTO(
    val id: Int?,
    val givenName: String?,
    val familyName: String?,
    val gender: String?,
    val birthDate: String?,
    val height: Int?,
    val eyecolor: String?,
    val email: String?,
    val taxId: String?,
    val addressAddressCountry: String?,
    val addressAddressLocality: String?,
    val addressPostalCode: Int?,
    val addressStreetAddress: String?,
    val addressHouseNumber: String?,
    val phoneNo: String?,
    val mobileNo: String?,
    val bankAccountBankCity: String?,
    val bankAccountBankBankCode: Int?,
    val bankAccountBankDesc: String?,
    val bankAccountBankBic: String?,
    val bankAccountIban: String?,
    val creditCardNumber: String?,
    val creditCardType: String?,
    val creditCardCvc: Int?,
    val birthName: String?,
) : Serializable