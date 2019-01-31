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

        return vbox {
            val filepreview = SimpleStringProperty("No file loaded")
            hbox {
                var file = File.createTempFile("Hans", "Wurst")
                val filename = SimpleStringProperty("No file selected")
                textfield().bind(filename, true)
                button("…").apply {
                    onAction = EventHandler {
                        val filters = arrayOf(FileChooser.ExtensionFilter("CSV files", "*.csv"))
                        val files = chooseFile(
                                title = "Select file to import",
                                filters = filters,
                                mode = FileChooserMode.Single
                        )
                        if (files.isNotEmpty()) {
                            file = files[0]
                            filename.set(file.name)
                            val fileContentPreview = StringBuffer()
                            file.useLines { lines: Sequence<String> ->
                                lines
                                        .take(3)
                                        .forEach { l -> fileContentPreview.append(l).append('\n') }
                            }
                            filepreview.set(fileContentPreview.toString())
                        }
                    }
                }

                button("import data").apply {
                    onAction = EventHandler {
                        importData(file)
                    }
                }
            }
            textarea().bind(filepreview, true).apply {
                prefHeight = 80.0
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
