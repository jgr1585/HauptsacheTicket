package at.fhv.teamh.hauptsach_ticket.frontend.components

import at.fhv.teamh.hauptsach_ticket.frontend.communication.RemoteFacade
import at.fhv.teamh.hauptsach_ticket.frontend.data.Message
import at.fhv.teamh.hauptsach_ticket.library.dto.MessageDTO
import at.fhv.teamh.hauptsach_ticket.library.dto.TopicDTO
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView
import javafx.geometry.Pos
import javafx.scene.control.TextArea
import javafx.scene.control.TextField
import javafx.scene.paint.Color
import javafx.scene.text.FontWeight
import org.controlsfx.control.Notifications
import tornadofx.*
import java.time.LocalDateTime
import java.util.*

class MessageCreate(message: Message? = null) : View() {
    private val topic = combobox<String> {
        prefWidth = 200.0
        prefHeight = 50.0
        cellFormat {
            text = it
            style {
                fontWeight = FontWeight.BOLD
                fontSize = 20.px
            }
        }
        items = RemoteFacade.ldapUser.allTopics
            .map { it.name }
            .toObservable()
    }
    private val messageTitle = TextField(message?.title?.value ?: "")
    private val messageBody = TextArea(message?.body?.value ?: "")

    override val root = vbox {
        spacing = 10.0
        paddingAll = 20.0
        alignment = Pos.CENTER_LEFT

        form {
            fieldset {
                field("Topic") {
                    add(topic)
                }
                field("Title") {
                    add(messageTitle)
                }
                field("Message") {
                    add(messageBody)
                }
            }

        }

        hbox {
            alignment = Pos.CENTER_RIGHT
            button("Publish") {
                graphic = FontAwesomeIconView(FontAwesomeIcon.PAPER_PLANE).apply {
                }
                action {
                    if (!(topic.value.isNullOrBlank() || messageTitle.text.isNullOrBlank() || messageBody.text.isNullOrBlank())) {
                        val messageDTO = MessageDTO(
                            UUID.randomUUID(),
                            messageTitle.text,
                            messageBody.text,
                            LocalDateTime.now(),
                            TopicDTO(topic.value)
                        )

                        topic.value = null
                        messageTitle.text = ""
                        messageBody.text = ""

                        RemoteFacade.getMessageService().publish(messageDTO)
                    } else {
                        Notifications.create()
                            .title("Something went wrong")
                            .text("Some fields are empty")
                            .darkStyle()
                            .showWarning()
                    }
                }
                style {
                    backgroundColor += Color.BLUE
                    fontSize = 20.px
                    fontWeight = FontWeight.BOLD
                }
            }
        }
    }
}
