package net.emteeware

import javafx.event.EventHandler
import javafx.scene.Node
import tornadofx.*


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
                prefHeight = 120.0
            }
            hbox {
                label("Field separator: ")
                textfield().bind(controller.separatorString)
            }
        }
    }


    private fun centerVBox(): Node {
        return vbox {

            tableview(media) {
                readonlyColumn("Date", Media::watchedAt)
                readonlyColumn("Title", Media::title)
                readonlyColumn("Type", Media::mediaType)
                readonlyColumn("Rating", Media::yourRating)
                columnResizePolicy = SmartResize.POLICY
            }
        }
    }
}
