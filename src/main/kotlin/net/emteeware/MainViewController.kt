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
import java.nio.file.Files
import java.util.prefs.Preferences

private const val PREFS_LAST_USED_DIR_KEY = "lastUsedDirectory"
private const val HEADER_ROW_COUNT = 1

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

    private fun updateInitialDirectory() {
        prefs.put(PREFS_LAST_USED_DIR_KEY, file.path.dropLast(file.name.length))
    }

    fun loadFile(chosenFiles: List<File>) {
        if (chosenFiles.isNotEmpty()) {
            file = chosenFiles[0]
            filename.set(file.name)
            lineCountString.set("${Files.lines(file.toPath()).count() - HEADER_ROW_COUNT} entries found")
            updateInitialDirectory()
            val fileContentPreview = StringBuffer()
            file.useLines { lines: Sequence<String> ->
                lines
                        .take(3)
                        .forEach { l -> fileContentPreview.append(l).append('\n') }
            }
            filepreview.set(fileContentPreview.toString())
            importDisabled.set(false)
        }
    }
}