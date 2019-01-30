package net.emteeware

import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import javafx.event.EventHandler
import javafx.scene.Node
import javafx.stage.FileChooser
import tornadofx.*
import java.io.File

class MainView : View("My View") {

    private var media = FXCollections.observableArrayList<Media>()
    override val root = borderpane {
        top = menuBox()
        center = centerVBox()
    }

    private fun menuBox(): Node {

        return hbox {
            var file = File.createTempFile("Hans", "Wurst")
            val filename = SimpleStringProperty("No file selected")
            textfield().bind(filename, true)
            button("…").apply {
                onAction = EventHandler {
                    val fileChooser = FileChooser()
                    file = fileChooser.showOpenDialog(null)
                    filename.set(file.name)
                }
            }

            button("import data").apply {
                onAction = EventHandler {
                    importData(file)
                }
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
