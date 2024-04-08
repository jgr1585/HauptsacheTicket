package at.fhv.teamh.hauptsach_ticket.frontend.app

import javafx.scene.Node
import javafx.scene.control.TableCell
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.util.Callback
import tornadofx.onChange

object Defaults {
    const val maxAvailableSize = Int.MAX_VALUE.toDouble()


    fun <S> TableView<S>.autoResize() {
        widthProperty().onChange { event ->
            val width = event / columns.size - 1
            columns.forEach {
                it.prefWidth = width
            }
        }
    }

    fun <T, S> createCellFactory(node: TableCell<T, S>.() -> Node): Callback<TableColumn<T, S>, TableCell<T, S>> {
        return Callback<TableColumn<T, S>, TableCell<T, S>> {
            object : TableCell<T, S>() {
                override fun updateItem(item: S?, empty: Boolean) {
                    super.updateItem(item, empty)

                    graphic = if (empty || tableRow == null || tableRow.item == null) {
                        null
                    } else {
                        node(this)
                    }
                }
            }
        }
    }
}