package net.emteeware

import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import javafx.event.EventHandler
import javafx.scene.Node
import javafx.stage.FileChooser
import tornadofx.*
import java.io.File
import java.nio.file.Files
import java.util.prefs.Preferences

private const val PREFS_LAST_USED_DIR_KEY = "lastUsedDirectory"
private const val HEADER_ROW_COUNT = 1

class MainView : View("My View") {

    private val controller: MainViewController by inject()

    private val prefs = Preferences.userRoot().node(this.javaClass.name)
    private var media = controller.getMediaList()
    override val root = borderpane {
        top = menuBox()
        center = centerVBox()
    }

    private fun menuBox(): Node {

        return vbox {
            val filepreview = SimpleStringProperty("No file loaded")
            val lineCountString = SimpleStringProperty("No file selected")
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
                        ) {
                            initialDirectory = File(prefs[PREFS_LAST_USED_DIR_KEY, System.getProperty("user.home")])
                        }
                        if (files.isNotEmpty()) {
                            file = files[0]
                            filename.set(file.name)
                            lineCountString.set("${Files.lines(file.toPath()).count() - HEADER_ROW_COUNT} entries found")
                            prefs.put(PREFS_LAST_USED_DIR_KEY, file.path.dropLast(file.name.length))
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
                        controller.importData(file)
                    }
                }
                label().bind(lineCountString)
            }
            textarea().bind(filepreview, true).apply {
                prefHeight = 80.0
            }
        }
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
