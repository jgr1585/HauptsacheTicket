package at.fhv.teamh.hauptsach_ticket.frontend.components

import at.fhv.teamh.hauptsach_ticket.frontend.communication.RemoteFacade
import at.fhv.teamh.hauptsach_ticket.frontend.controller.ShoppingCartController
import at.fhv.teamh.hauptsach_ticket.frontend.view.*
import at.fhv.teamh.hauptsach_ticket.library.enums.Permission
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView
import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.ToolBar
import javafx.scene.layout.Background
import javafx.scene.layout.BackgroundFill
import javafx.scene.layout.CornerRadii
import javafx.scene.paint.Color
import tornadofx.*

class CustomToolBar: View() {
    private val mainView: MainView by inject()
    private val cartController: ShoppingCartController by inject()

    override val root = ToolBar().apply {
        padding = javafx.geometry.Insets(10.0, 0.0, 10.0, 0.0)
        if (RemoteFacade.permissions.contains(Permission.ADMIN) ||
            RemoteFacade.permissions.contains(Permission.SELLER)) {
            button {
                graphic = FontAwesomeIconView(FontAwesomeIcon.SEARCH).apply {
                    glyphSize = 64
                }
                action {
                    mainView.changeView(SearchView())
                }
            }
        }

        add(NotificationButton().apply {
            number.value = RemoteFacade.getMessageService().messages.size

            RemoteFacade.getMessageService().messages.onChange {
                number.value = it.list.size
            }

            button = Button().apply {
                graphic = FontAwesomeIconView(FontAwesomeIcon.ENVELOPE).apply {
                    glyphSize = 64
                }
                action {
                    mainView.changeView(MessageView())
                }
            }
        }.create())

        if (RemoteFacade.permissions.contains(Permission.ADMIN) ||
            RemoteFacade.permissions.contains(Permission.OPERATOR)
        ) {
            button {
                graphic = FontAwesomeIconView(FontAwesomeIcon.PAPER_PLANE).apply {
                    glyphSize = 64
                }
                action {
                    mainView.changeView(CreatMessageView())
                }
            }
        }

        button {
            graphic = FontAwesomeIconView(FontAwesomeIcon.RSS).apply {
                glyphSize = 64
            }
            action {
                mainView.changeView(RSSFeedView())
            }
        }

        if (RemoteFacade.permissions.contains(Permission.ADMIN) ||
            RemoteFacade.permissions.contains(Permission.SELLER)
        ) {
            add(NotificationButton().apply {
                number.value = cartController.items.size

                cartController.items.onChange {
                    number.value = cartController.items.size
                }

                button = Button().apply {
                    graphic = FontAwesomeIconView(FontAwesomeIcon.SHOPPING_CART).apply {
                        glyphSize = 64
                    }

                    action {
                        mainView.changeView(ShoppingCartView())
                    }
                }

            }.create())
        }

        button {
            graphic = FontAwesomeIconView(FontAwesomeIcon.ADDRESS_CARD).apply {
                glyphSize = 64
            }
            action {
                mainView.changeView(OrderCancelView())
            }
        }

        button {
            graphic = FontAwesomeIconView(FontAwesomeIcon.SIGN_OUT).apply {
                glyphSize = 64
            }
            action {
                RemoteFacade.disconnect()
                mainView.changeView(LoginView())
            }
        }
        style {
            alignment = Pos.CENTER_RIGHT
            paddingRight = 10
        }
    background = Background(BackgroundFill(Color.LIGHTGRAY, CornerRadii(10.0), null))
    }
}
