package net.emteeware

import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleStringProperty
import javafx.beans.value.ObservableStringValue
import javafx.beans.value.ObservableValue
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import tornadofx.*
import java.io.File

class MainViewController : Controller() {
    val media = FXCollections.observableArrayList<Media>()
    val filepreview = SimpleStringProperty("No file selected")
    val importDisabled = SimpleBooleanProperty(true)
    val filename = SimpleStringProperty("No file selected")
    var file =  File.createTempFile("Hans", "Wurst")

    fun getMediaList(): ObservableList<Media> {
        return media
    }

    fun importData(file: File) {
        val imdbViewingHistory = ImdbViewingHistory()
        imdbViewingHistory.importFromCsv(file.canonicalPath)
        media.setAll(imdbViewingHistory.getTraktMediaList())
    }
}