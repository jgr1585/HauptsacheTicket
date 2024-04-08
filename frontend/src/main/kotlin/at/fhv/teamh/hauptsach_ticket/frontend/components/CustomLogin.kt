package at.fhv.teamh.hauptsach_ticket.frontend.components

import at.fhv.teamh.hauptsach_ticket.frontend.communication.RemoteFacade
import at.fhv.teamh.hauptsach_ticket.frontend.view.SearchView
import javafx.beans.property.SimpleStringProperty
import javafx.geometry.Pos
import javafx.scene.effect.DropShadow
import javafx.scene.input.KeyCode
import javafx.scene.paint.Color
import javafx.scene.text.Font
import javafx.stage.Screen
import tornadofx.*


class CustomLogin : View() {
    private val connection = SimpleStringProperty("10.0.40.174")
    private val userName = SimpleStringProperty("")
    private val password = SimpleStringProperty("")

    override val root = hbox {
        alignment = Pos.CENTER

        form {
            prefHeight = Screen.getPrimary().visualBounds.height
            alignment = Pos.CENTER

            vbox {
                alignment = Pos.CENTER

                fieldset {
                    field("Server") {
                        textfield(connection) {
                            prefHeight = 64.0
                            prefWidth = 200.0
                            font = Font.font(20.0)
                            effect = DropShadow(10.0, Color.GRAY)
                        }
                    }
                    field("Username") {
                        textfield(userName) {
                            prefHeight = 64.0
                            prefWidth = 200.0
                            font = Font.font(20.0)
                            effect = DropShadow(10.0, Color.GRAY)
                            setOnKeyPressed { event ->
                                if (event.code == KeyCode.ENTER) {
                                    loginButtonFunctionality()
                                }
                            }
                        }
                    }
                    field("Password") {
                        passwordfield(password) {
                            prefHeight = 64.0
                            prefWidth = 200.0
                            font = Font.font(20.0)
                            effect = DropShadow(10.0, Color.GRAY)
                            setOnKeyPressed { event ->
                                if (event.code == KeyCode.ENTER) {
                                    loginButtonFunctionality()
                                }
                            }
                        }
                    }
                }

                hbox {
                    alignment = Pos.CENTER_RIGHT

                    button("Login") {
                        minWidth = 350.0
                        minHeight = 64.0
                        font = Font.font(25.0)
                        action {
                            loginButtonFunctionality()
                        }

                        setOnMousePressed {
                            style {
                                backgroundColor += Color.LIGHTBLUE
                            }
                        }
                        setOnMouseReleased {
                            style {
                                backgroundColor += Color.GRAY
                            }
                        }
                    }
                }
            }
        }
    }

    private fun loginButtonFunctionality() {
        val canConnect = RemoteFacade.connect(connection.value, userName.value, password.value)

        if (!canConnect) {
            error(
                "Could not connect to server",
                "Please check your connection settings and try again."
            )
            return
        } else {
            replaceWith(SearchView::class)
        }
    }
}
