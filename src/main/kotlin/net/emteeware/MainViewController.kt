package net.emteeware

import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleStringProperty
import javafx.beans.value.ObservableStringValue
import javafx.beans.value.ObservableValue
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.stage.FileChooser
import tornadofx.*
import java.io.File
import java.util.prefs.Preferences

private const val PREFS_LAST_USED_DIR_KEY = "lastUsedDirectory"

class MainViewController : Controller() {
    private val prefs = Preferences.userRoot().node(this.javaClass.name)
    val media = FXCollections.observableArrayList<Media>()
    val filepreview = SimpleStringProperty("No file selected")
    val importDisabled = SimpleBooleanProperty(true)
    val filename = SimpleStringProperty("No file selected")
    var file =  File.createTempFile("Hans", "Wurst")
    val initialDirectory = File(prefs[PREFS_LAST_USED_DIR_KEY, System.getProperty("user.home")])
    val filters = arrayOf(FileChooser.ExtensionFilter("CSV files", "*.csv"))
    val lineCountString = SimpleStringProperty("No file selected")

    fun getMediaList(): ObservableList<Media> {
        return media
    }

    fun importData(file: File) {
        val imdbViewingHistory = ImdbViewingHistory()
        imdbViewingHistory.importFromCsv(file.canonicalPath)
        media.setAll(imdbViewingHistory.getTraktMediaList())
    }

    fun updateInitialDirectory() {
        prefs.put(PREFS_LAST_USED_DIR_KEY, file.path.dropLast(file.name.length))
    }
}