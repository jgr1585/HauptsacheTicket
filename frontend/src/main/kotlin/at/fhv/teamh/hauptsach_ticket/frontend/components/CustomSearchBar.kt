package at.fhv.teamh.hauptsach_ticket.frontend.components

import at.fhv.teamh.hauptsach_ticket.frontend.communication.RemoteFacade
import at.fhv.teamh.hauptsach_ticket.frontend.controller.SearchTableDataController
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.geometry.Pos
import javafx.scene.input.KeyCode
import javafx.scene.layout.HBox
import javafx.scene.paint.Color
import javafx.util.Callback
import org.controlsfx.control.textfield.AutoCompletionBinding
import org.controlsfx.control.textfield.CustomTextField
import org.controlsfx.control.textfield.TextFields
import tornadofx.*
import java.time.LocalDate

@Suppress("NestedLambdaShadowedImplicitParameter")
class CustomSearchBar : View() {
    private val searchController: SearchTableDataController by inject()

    private val searchField = CustomTextField().apply {

        right = FontAwesomeIconView(FontAwesomeIcon.GEARS).apply {
            glyphSize = 24
            fill = Color.GRAY
            paddingRight = 10

            setOnMouseClicked { toggleSettingsDetailPane() }
        }

        TextFields.bindAutoCompletion(this, Callback<AutoCompletionBinding.ISuggestionRequest, Collection<String>> {
            RemoteFacade.searchEvents(it.userText).flatMap { listOf(it.series.name, it.series.artist) }.distinct()
        })
    }

    private val searchButton = button("Go") {
        graphic = FontAwesomeIconView(FontAwesomeIcon.SEARCH).apply {
            glyphSize = 24
            fill = Color.WHITE
        }
        setPrefSize(64.0, 64.0)
        setOnAction {
            performSearch()
        }
    }

    private val settingsDetailPane: HBox = hbox {

        alignment = Pos.CENTER
        paddingTop = 0
        paddingBottom = 10
        spacing = 10.0

        textfield(AdvancedSearch.eventName) {
            promptText = "Event Name"

            TextFields.bindAutoCompletion(this, Callback<AutoCompletionBinding.ISuggestionRequest, Collection<String>> {
                RemoteFacade.findEventName(it.userText).map { it.series.name }.distinct()
            })
        }

        textfield(AdvancedSearch.genre) {
            promptText = "Genre"

            TextFields.bindAutoCompletion(this, Callback<AutoCompletionBinding.ISuggestionRequest, Collection<String>> {
                RemoteFacade.findEventGenre(it.userText).map { it.genre }.distinct()
            })
        }

        datepicker(AdvancedSearch.date) {
            promptText = "Date"
        }
    }

    override val root = vbox {
        hbox {
            alignment = Pos.CENTER
            paddingTop = 10
            paddingBottom = 10
            spacing = 10.0 // Abstand zwischen Textfeld und Button

            add(searchField)
            add(searchButton)
        }

        add(settingsDetailPane)

        settingsDetailPane.hide()

        setOnKeyPressed {
            if (it.code == KeyCode.ENTER) {
                performSearch()
            }
        }
    }

    private fun toggleSettingsDetailPane() {
        if (settingsDetailPane.isVisible) {
            settingsDetailPane.hide()
        } else {
            settingsDetailPane.show()
        }
    }

    private fun performSearch() {
        val searchText = searchField.text
        var eventName: String? = null
        var genre: String? = null
        var date: LocalDate? = null

        if (settingsDetailPane.isVisible) {
            if (AdvancedSearch.eventName.value.isNullOrBlank()) {
                AdvancedSearch.eventName.value = null
            }

            if (AdvancedSearch.genre.value.isNullOrBlank()) {
                AdvancedSearch.genre.value = null
            }

            eventName = AdvancedSearch.eventName.value
            genre = AdvancedSearch.genre.value
            date = AdvancedSearch.date.value
        }

        val events = RemoteFacade.searchEvents(searchText, eventName, genre, date)
        searchController.updateTable(events, searchText, eventName, genre, date)
    }

    private object AdvancedSearch {
        val eventName = SimpleStringProperty()
        val genre = SimpleStringProperty()
        val date = SimpleObjectProperty<LocalDate>()
    }

}


