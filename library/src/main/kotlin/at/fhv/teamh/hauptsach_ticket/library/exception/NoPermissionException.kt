package at.fhv.teamh.hauptsach_ticket.library.exception

class NoPermissionException : Exception() {
    override val message: String
        get() = "No Permission for this Operation"
}