package net.emteeware

import javafx.collections.FXCollections
import javafx.event.EventHandler
import javafx.scene.Node
import javafx.stage.FileChooser
import tornadofx.*
import java.io.File
import java.net.URL
import java.time.LocalDate

class MainView : View("My View") {

    private var media = FXCollections.observableArrayList<Media>()
    override val root = borderpane {
        top = menuBox()
        center = centerVBox()
    }

    private fun menuBox(): Node {
        return vbox {
            button("import data").apply {
                onAction = EventHandler {
                    val fileChooser = FileChooser()
                    val file = fileChooser.showOpenDialog(null)
                    importData(file) }
            }
        }

    }

    private fun importData(file: File) {
        val imdbViewingHistory = ImdbViewingHistory()
        imdbViewingHistory.importFromCsv(file.canonicalPath)
        media.setAll(imdbViewingHistory.getTraktMediaList())
    }

    private fun centerVBox(): Node {
        return vbox {

            tableview(media) {
                readonlyColumn("Date", Media::watchedAt)
                readonlyColumn("Title", Media::title)
                readonlyColumn("Type", Media::mediaType)
                columnResizePolicy = SmartResize.POLICY
            }
        }
    }
}
