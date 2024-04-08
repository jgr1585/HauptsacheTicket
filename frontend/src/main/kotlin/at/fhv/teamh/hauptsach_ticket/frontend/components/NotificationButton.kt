package at.fhv.teamh.hauptsach_ticket.frontend.components

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView
import javafx.application.Platform
import javafx.beans.property.SimpleIntegerProperty
import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.layout.AnchorPane
import javafx.scene.paint.Paint
import javafx.scene.shape.StrokeType
import tornadofx.*


class NotificationButton {

    var button = Button().apply {
        graphic = FontAwesomeIconView(FontAwesomeIcon.BELL).apply {
            glyphSize = 64
        }
    }

    val number = SimpleIntegerProperty(5)

    fun create() = AnchorPane().apply {
        add(button)

        stackpane {
            AnchorPane.setRightAnchor(this, -2.0)
            AnchorPane.setTopAnchor(this, -8.0)
            isPickOnBounds = false

            val runnable = {
                isVisible = number.value > 0
            }

            Platform.runLater(runnable)

            number.onChange {
                Platform.runLater(runnable)
            }

            ellipse {
                radiusX = 13.0
                radiusY = 13.0
                fill = Paint.valueOf("red")
                stroke = Paint.valueOf("LightGray")
                isPickOnBounds = false

                strokeWidth = 0.5
                strokeType = StrokeType.INSIDE
            }

            label {
                textProperty().bind(number.asString())
                isPickOnBounds = false

                alignment = Pos.CENTER

                style {
                    alignment = Pos.CENTER
                    textFill = Paint.valueOf("white")
                }
            }
        }
    }
}