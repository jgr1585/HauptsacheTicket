package at.fhv.teamh.hauptsach_ticket.backend.application

import java.net.InetAddress

object Environment {

    val ldapHosts = mapOf(
        "main" to "10.0.40.174:389",
        "dev" to "hauptsachTicket-ldap:1389"
    )

    val customerDBClientHost = mapOf(
        "main" to "10.0.40.175",
        "dev" to "hauptsachTicket-customer-db-java",
    )
    const val customerDBClientPort = 1077

    val activeMQHost = mapOf(
        "main" to "10.0.40.174",
        "dev" to "localhost",
    )
    const val activeMQPort = 61616

    val isDevEnvironment = checkIfDevEnvironment()
    private fun checkIfDevEnvironment(): Boolean {
        return try {
            val devAddress = InetAddress.getByName("hauptsachTicket-backend-db")
            devAddress.isReachable(1000).also {
                println("Environment: ${if (it) "dev" else "main"}")
            }
        } catch (e: Exception) {
            false
        }
    }
}