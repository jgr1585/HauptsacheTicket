package at.fhv.teamh.hauptsach_ticket.library.exception

class AuthenticationFailedException : Exception() {
    override val message: String
        get() = "Authentication failed"
}