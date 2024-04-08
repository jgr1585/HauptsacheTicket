package at.fhv.teamh.hauptsach_ticket.frontend.app

import javafx.scene.effect.DropShadow
import javafx.scene.paint.Color
import javafx.scene.paint.Paint
import javafx.scene.text.Font
import javafx.scene.text.FontWeight
import tornadofx.*

class Styles : Stylesheet() {
    init {
        root {
            baseColor = c(60, 63, 65)
        }

        label {
            padding = box(10.px)
            fontSize = 20.px
            fontWeight = FontWeight.BOLD
        }

        textField {
            prefWidth = 350.px
            minHeight = 40.px
            prefHeight = 64.px
            padding = box(0.0.px, 10.0.px)
            font = Font.font(20.0)
            effect = DropShadow(10.0, Color.GRAY)
            borderRadius = multi(box(20.px))
            backgroundRadius = multi(box(20.px))
        }

        passwordField {
            prefWidth = 350.px
            minHeight = 40.px
            prefHeight = 64.px
            padding = box(0.0.px, 10.0.px)
            font = Font.font(20.0)
            effect = DropShadow(10.0, Color.GRAY)
            borderRadius = multi(box(20.px))
            backgroundRadius = multi(box(20.px))
        }

        tableView {
            baseColor=c(105,113,118)

            odd {
                backgroundColor = multi(Paint.valueOf("#98A2AB"))
            }
        }

        button {
            backgroundColor = multi(Paint.valueOf("Gray"))
            borderRadius = multi(box(20.px))
            backgroundRadius = multi(box(20.px))

        }

        s(".popover > .content") {
            backgroundColor = multi(Paint.valueOf("rgb(60,63,65)"))
        }

    }
}