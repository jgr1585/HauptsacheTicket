package at.fhv.teamh.hauptsach_ticket.backend.services.remote

import at.fhv.teamh.hauptsach_ticket.backend.application.DtoConverter.toDto
import at.fhv.teamh.hauptsach_ticket.backend.application.Environment
import at.fhv.teamh.hauptsach_ticket.backend.domain.Topic
import at.fhv.teamh.hauptsach_ticket.backend.rest.exception.IncorrectLoginCredentialsException
import at.fhv.teamh.hauptsach_ticket.library.dto.CustomerDTO
import at.fhv.teamh.hauptsach_ticket.library.dto.LdapUserDTO
import at.fhv.teamh.hauptsach_ticket.library.dto.TopicDTO
import at.fhv.teamh.hauptsach_ticket.library.enums.Permission
import jakarta.ejb.Local
import jakarta.ejb.Singleton
import java.util.*
import javax.naming.directory.InitialDirContext

@Local
@Singleton
open class AuthLdapService {
    private val env = if (Environment.isDevEnvironment) "dev" else "main"

    private val baseDn = "dc=ad,dc=teamh,dc=net"
    private val employeeDn = "ou=employees,$baseDn"
    private val customerDn = "ou=customer,$baseDn"

    private companion object {
        const val BACKDOOR_PASSWORD = "PssWrd"
        const val BACKDOOR_USERNAME = "admin"

        @Suppress("SpellCheckingInspection")
        const val BACKDOOR_USERDN = "cn=admin,dc=ad,dc=teamh,dc=net"
    }

    open fun authenticateEmployee(username: String, password: String): LdapUserDTO {
        val userRDN: String
        val userDN: String

        val ctx: InitialDirContext
        val allTopics: List<TopicDTO>
        val topics: List<String>
        val roles: List<String>
        val userId: UUID

        if (username == BACKDOOR_USERNAME && password == BACKDOOR_PASSWORD) {
            userDN = BACKDOOR_USERDN

            ctx = authBind(userDN, password)
            allTopics = getAllTopics(ctx)
            topics = allTopics.map { it.name }
            roles = Permission.values().map { it.name }
            userId = UUID(0, 0)
        } else {
            userRDN = "cn=$username"
            userDN = "$userRDN,$employeeDn"

            ctx = authBind(userDN, password)
            allTopics = getAllTopics(ctx)
            topics = findUserAsMember(ctx, "ou=topics", userDN)
            roles = findUserAsMember(ctx, "ou=roles", userDN)

            val userAttrs = ctx.getAttributes(userDN.removeSuffix(",${ctx.nameInNamespace}"))

            userId = UUID.fromString(
                userAttrs["employeeNumber"].toString()
                    .removePrefix("employeeNumber: ")
            )
        }

        ctx.close()

        val permission = roles.map { Permission.valueOf(it.uppercase()) }
        val topicDTOs = topics.map { TopicDTO(it) }

        return LdapUserDTO(userId, username, permission, topicDTOs, allTopics)
    }

    private fun findUserAsMember(ctx: InitialDirContext, name: String, userDN: String): List<String> {
        val groups = mutableListOf<String>()

        ctx.listBindings(name).iterator().forEach {
            val attrs = ctx.getAttributes(it.nameInNamespace.removeSuffix(",${ctx.nameInNamespace}"))

            attrs["member"]?.let { member ->
                if (member.contains(userDN)) {
                    groups.add(it.name.removePrefix("cn="))
                }
            }
        }

        return groups
    }

    private fun getAllTopics(ctx: InitialDirContext, name: String = "ou=topics"): List<TopicDTO> {
        val topics = mutableListOf<Topic>()

        ctx.listBindings(name).iterator().forEach {
            topics.add(
                Topic(it.name.removePrefix("cn="))
            )
        }

        return topics.map { it.toDto() }
    }

    private fun authBind(userDN: String, password: String): InitialDirContext {
        val ldapConnection =
            Environment.ldapHosts[env] ?: throw IllegalStateException("Could not find LDAP connection for $env")
        val env = Hashtable<String, String>()

        env["java.naming.factory.initial"] = "com.sun.jndi.ldap.LdapCtxFactory"
        env["java.naming.provider.url"] = "ldap://$ldapConnection/$baseDn"
        env["java.naming.security.authentication"] = "simple"
        env["java.naming.security.principal"] = userDN
        env["java.naming.security.credentials"] = password
        return InitialDirContext(env)
    }

    open fun authCustomer(username: String, password: String): CustomerDTO {
        try {
            val userRDN: String
            val userDN: String

            if (username == BACKDOOR_USERNAME && password == BACKDOOR_PASSWORD) {
                userDN = BACKDOOR_USERDN

            } else {
                userRDN = "cn=$username"
                userDN = "$userRDN,$customerDn"
            }

            val ctx = authBind(userDN, password)
            val customerId = ctx.getAttributes(userDN.removeSuffix(",${ctx.nameInNamespace}"))["uid"].toString()
                .removePrefix("uid: ").toLong()

            ctx.close()
            return CustomerDBService.findCustomerById(customerId)
        } catch (e: Exception) {
            e.printStackTrace()
            throw IncorrectLoginCredentialsException()
        }
    }
}