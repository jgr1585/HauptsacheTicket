package at.fhv.teamh.hauptsach_ticket.customerDB

import at.fhv.teamh.hauptsach_ticket.customerDB.communication.CustomerDBClientFactoryImpl
import java.net.NetworkInterface
import java.rmi.Naming
import java.rmi.registry.LocateRegistry

fun main() {
    val port = 1077
    val logger = mu.KotlinLogging.logger {}

    LocateRegistry.createRegistry(port)
    logger.info("Registry started on port $port")


    NetworkInterface.getNetworkInterfaces().toList()
        .filter { !it.isVirtual && it.isUp }
        .map { nit ->
            return@map nit.inetAddresses.toList()
                .filter { !it.isMulticastAddress && it !is java.net.Inet6Address }
                .map { it.hostAddress }
        }
        .flatten()
        .forEach {
            logger.info("Binding to $it")
            Naming.rebind("rmi://${it}:$port/CustomerDBClientFactory", CustomerDBClientFactoryImpl())
        }

    logger.info("CustomerDB bound to registry")
}