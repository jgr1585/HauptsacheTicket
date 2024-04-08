package at.fhv.teamh.hauptsach_ticket.frontend.communication

import at.fhv.teamh.hauptsach_ticket.frontend.data.Topic
import at.fhv.teamh.hauptsach_ticket.library.dto.LdapUserDTO
import at.fhv.teamh.hauptsach_ticket.library.dto.MessageDTO
import at.fhv.teamh.hauptsach_ticket.library.dto.TopicDTO
import javafx.application.Platform
import org.apache.activemq.ActiveMQConnectionFactory
import tornadofx.observableListOf
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*
import java.util.concurrent.TimeUnit
import javax.jms.*

class MessageService(private val ldapUser: LdapUserDTO) {
    private val subscriberConnection = createConnection(ldapUser)
    private val threads = mutableListOf<Thread>()
    val messages = observableListOf<Message>()

    companion object {
        private val TTL = TimeUnit.DAYS.toMillis(7)

        internal fun Message.toDTO() =
            MessageDTO(
                id = UUID.fromString(getStringProperty("id")),
                title = getStringProperty("title"),
                body = (this as TextMessage).text,
                sendTimestamp = LocalDateTime.ofInstant(Instant.ofEpochSecond(jmsTimestamp), ZoneId.systemDefault()),
                topicDTO = TopicDTO(jmsDestination
                    .toString()
                    .removePrefix("topic://")
                    .replaceFirstChar { it.uppercase() })
            )
    }

    fun publish(message: MessageDTO) {
        subscriberConnection.createSession(false, Session.CLIENT_ACKNOWLEDGE).use { session ->
            session.createProducer(session.createTopic(message.topicDTO.name)).apply {
                deliveryMode = DeliveryMode.PERSISTENT
                timeToLive = TTL
            }.use { producer ->
                producer.send(
                    session.createTextMessage(message.body).apply {
                        setStringProperty("id", message.id.toString())
                        setStringProperty("title", message.title)
                    }
                )
            }
        }
    }

    fun subscribe() {
        val userID = ldapUser.id
        val topics = ldapUser.subscribedTopics.map { Topic(it.name) }

        val session = subscriberConnection.createSession(false, Session.CLIENT_ACKNOWLEDGE)

        topics.map {
            session.createDurableConsumer(it, "${userID}_${it.name}")
        }.forEach { messageConsumer ->
            Thread {
                try {
                    var message: Message? = messageConsumer.receive()

                    while (message != null) {
                        println("received message ${message.getStringProperty("id")} from ${message.jmsDestination}")

                        message.run {
                            Platform.runLater {
                                messages.add(this)
                            }
                        }

                        message = messageConsumer.receive()
                    }

                } catch (e: InterruptedException) {
                    println("interrupted")
                } catch (e: JMSException) {
                    println("JMSException: ${e.message}")
                }
            }.apply {
                isDaemon = true
                threads.add(this)
            }.start()

        }
    }

    fun acknowledge(message: Message) {
        Platform.runLater {
            messages.remove(message)
        }
    }


    fun close() {
        threads.forEach { it.interrupt() }
        subscriberConnection.close()
    }

    private fun createConnection(ldapUser: LdapUserDTO): Connection {
        val connectionFactory = ActiveMQConnectionFactory(RemoteFacade.getActiveMQBroker()).apply {
            closeTimeout = TimeUnit.DAYS.toMillis(7).toInt()
        }

        return connectionFactory.createConnection().apply {
            clientID = ldapUser.id.toString()
            start()
        }
    }
}