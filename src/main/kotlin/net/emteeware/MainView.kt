package net.emteeware

import javafx.beans.property.SimpleStringProperty
import javafx.event.EventHandler
import javafx.scene.Node
import javafx.stage.FileChooser
import tornadofx.*
import java.io.File
import java.nio.file.Files
import java.util.prefs.Preferences



class MainView : View("My View") {

    private val controller: MainViewController by inject()


    private var media = controller.getMediaList()
    override val root = borderpane {
        top = menuBox()
        center = centerVBox()
    }

    private fun menuBox(): Node {

        return vbox {
            hbox {
                textfield().bind(controller.filename, true)
                button("…").apply {
                    onAction = EventHandler {
                        controller.loadFile(chooseFile(
                                title = "Select file to import",
                                filters = controller.filters,
                                mode = FileChooserMode.Single
                        ) {
                            initialDirectory = controller.initialDirectory
                        })
                    }
                }

                button("import data").apply {
                    onAction = EventHandler {
                        controller.importData(controller.file)
                    }
                    disableProperty().bind(controller.importDisabled)
                }
                label().bind(controller.lineCountString)
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
