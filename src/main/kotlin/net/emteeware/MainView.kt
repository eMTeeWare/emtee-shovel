package net.emteeware

import javafx.beans.property.SimpleStringProperty
import javafx.event.EventHandler
import javafx.scene.Node
import javafx.stage.FileChooser
import tornadofx.*
import java.io.File
import java.nio.file.Files
import java.util.prefs.Preferences


private const val HEADER_ROW_COUNT = 1

class MainView : View("My View") {

    private val controller: MainViewController by inject()


    private var media = controller.getMediaList()
    override val root = borderpane {
        top = menuBox()
        center = centerVBox()
    }

    private fun menuBox(): Node {

        return vbox {
            val lineCountString = SimpleStringProperty("No file selected")
            hbox {
                textfield().bind(controller.filename, true)
                button("…").apply {
                    onAction = EventHandler {
                        val filters = arrayOf(FileChooser.ExtensionFilter("CSV files", "*.csv"))
                        val files = chooseFile(
                                title = "Select file to import",
                                filters = filters,
                                mode = FileChooserMode.Single
                        ) {
                            initialDirectory = controller.initialDirectory
                        }
                        if (files.isNotEmpty()) {
                            controller.file = files[0]
                            controller.filename.set(controller.file.name)
                            lineCountString.set("${Files.lines(controller.file.toPath()).count() - HEADER_ROW_COUNT} entries found")
                            controller.updateInitialDirectory()
                            val fileContentPreview = StringBuffer()
                            controller.file.useLines { lines: Sequence<String> ->
                                lines
                                        .take(3)
                                        .forEach { l -> fileContentPreview.append(l).append('\n') }
                            }
                            controller.filepreview.set(fileContentPreview.toString())
                            controller.importDisabled.set(false)
                        }
                    }
                }

                button("import data").apply {
                    onAction = EventHandler {
                        controller.importData(controller.file)
                    }
                    disableProperty().bind(controller.importDisabled)
                }
                label().bind(lineCountString)
            }
            textarea().bind(controller.filepreview, true).apply {
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
                readonlyColumn("Rating", Media::YourRating)
                columnResizePolicy = SmartResize.POLICY
            }
        }
    }
}
