package net.emteeware

import javafx.collections.FXCollections
import javafx.collections.ObservableList
import tornadofx.*
import java.io.File

class MainViewController : Controller() {
    val media = FXCollections.observableArrayList<Media>()

    fun getMediaList(): ObservableList<Media> {
        return media
    }

    fun importData(file: File) {
        val imdbViewingHistory = ImdbViewingHistory()
        imdbViewingHistory.importFromCsv(file.canonicalPath)
        media.setAll(imdbViewingHistory.getTraktMediaList())
    }
}