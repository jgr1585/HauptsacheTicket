package at.fhv.teamh.hauptsach_ticket.customerDB.domain

import jakarta.persistence.*

@Entity
@Table(name = "customer")
data class Customer(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val id: Int,

    @Column(name = "\"givenName\"")
    val givenName: String?,

    @Column(name = "\"familyName\"")
    val familyName: String?,

    @Column(name = "gender")
    val gender: String?,

    @Column(name = "\"birthDate\"")
    val birthDate: String?,

    @Column(name = "height")
    val height: Int?,

    @Column(name = "eyecolor")
    val eyecolor: String?,

    @Column(name = "email")
    val email: String?,

    @Column(name = "\"taxId\"")
    val taxId: String?,

    @Column(name = "\"address.addressCountry\"")
    val addressAddressCountry: String?,

    @Column(name = "\"address.addressLocality\"")
    val addressAddressLocality: String?,

    @Column(name = "\"address.postalCode\"")
    val addressPostalCode: Int?,

    @Column(name = "\"address.streetAddress\"")
    val addressStreetAddress: String?,

    @Column(name = "\"address.houseNumber\"")
    val addressHouseNumber: String?,

    @Column(name = "\"phoneNo\"")
    val phoneNo: String?,

    @Column(name = "\"mobileNo\"")
    val mobileNo: String?,

    @Column(name = "\"bankAccount.bank.city\"")
    val bankAccountBankCity: String?,

    val bankAccountBankBankCode: Int?,

    @Column(name = "\"bankAccount.bank.desc\"")
    val bankAccountBankDesc: String?,

    @Column(name = "\"bankAccount.bank.bic\"")
    val bankAccountBankBic: String?,

    @Column(name = "\"bankAccount.iban\"")
    val bankAccountIban: String?,

    @Column(name = "\"creditCard.number\"")
    val creditCardNumber: String?,

    @Column(name = "\"creditCard.type\"")
    val creditCardType: String?,

    @Column(name = "\"creditCard.cvc\"")
    val creditCardCvc: Int?,

    @Column(name = "\"birthName\"")
    val birthName: String?,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Customer

        return id == other.id
    }

    override fun hashCode(): Int {
        return id
    }

    override fun toString(): String {
        return "Customer(id=$id, givenName=$givenName, familyName=$familyName, gender=$gender, birthDate=$birthDate, height=$height, eyecolor=$eyecolor, email=$email, taxId=$taxId, addressAddressCountry=$addressAddressCountry, addressAddressLocality=$addressAddressLocality, addressPostalCode=$addressPostalCode, addressStreetAddress=$addressStreetAddress, addressHouseNumber=$addressHouseNumber, phoneNo=$phoneNo, mobileNo=$mobileNo, bankAccountBankCity=$bankAccountBankCity, bankAccountBankBankCode=$bankAccountBankBankCode, bankAccountBankDesc=$bankAccountBankDesc, bankAccountBankBic=$bankAccountBankBic, bankAccountIban=$bankAccountIban, creditCardNumber=$creditCardNumber, creditCardType=$creditCardType, creditCardCvc=$creditCardCvc, birthName=$birthName)"
    }
}