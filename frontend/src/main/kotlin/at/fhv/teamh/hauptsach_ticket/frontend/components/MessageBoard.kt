package at.fhv.teamh.hauptsach_ticket.frontend.components

import at.fhv.teamh.hauptsach_ticket.frontend.communication.MessageService.Companion.toDTO
import at.fhv.teamh.hauptsach_ticket.frontend.communication.RemoteFacade
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.paint.Color
import javafx.scene.text.FontWeight
import tornadofx.*

class MessageBoard : View("Message View") {
    private val message = RemoteFacade.getMessageService().messages


    override val root = vbox {
        spacing = 10.0
        alignment = Pos.CENTER
        padding = Insets(10.0, 0.0, 10.0, 0.0)

        add(getAccordion())
        message.onChange {
            replaceChildren(getAccordion())
        }
    }

    private fun getAccordion() = accordion {
        message.groupBy { it.toDTO().topicDTO.name }.forEach { (topic, messages) ->
            val titledPane = titledpane(topic) {
                isExpanded = false
                vbox {
                    messages.forEach { message ->
                        val messageDto = message.toDTO()
                        val titleMessageTitledPane = titledpane(messageDto.title) {
                            isExpanded = false
                            vbox {
                                label(messageDto.body) {
                                    isWrapText = true
                                }

                                button("acknowledge") {
                                    action {
                                        Thread {
                                            RemoteFacade.getMessageService().acknowledge(message)
                                        }.start()

                                        this.isDisable = true
                                    }
                                    style {
                                        backgroundColor += Color.BLUE
                                        fontSize = 20.px
                                        fontWeight = FontWeight.BOLD
                                        alignment = Pos.CENTER_RIGHT
                                    }
                                }
                            }
                        }
                        add(titleMessageTitledPane)
                    }
                }
            }
            panes.add(titledPane)
        }
    }
}