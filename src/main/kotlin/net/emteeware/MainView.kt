package net.emteeware

import javafx.event.EventHandler
import javafx.scene.layout.BorderPane
import tornadofx.*


class MainView : View("My View") {

    private val controller: MainViewController by inject()
    val model = MediaModel(Media())

    override val root = BorderPane()
    private var media = controller.getMediaList()

    init {
        with(root) {
            top {
                vbox {
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
            center {
                vbox {
                    hbox {
                        tableview(media) {
                            column("Date", Media::watchedAtProperty)
                            column("Title", Media::titleProperty)
                            column("Type", Media::mediaTypeProperty)
                            column("Rating", Media::yourRatingProperty)
                            columnResizePolicy = SmartResize.POLICY
                            model.rebindOnChange(this) { selectedMedia ->
                                medium = selectedMedia ?: Media()
                                println("rebindOnChange: $medium")
                            }
                            onSelectionChange { selmed -> println("onChange: $selmed") }
                        }
                        form {
                            fieldset("Selected media") {
                                field("Title: ") { textfield(model.title) }
                                field("Type: ") { textfield(model.mediaType.toString()) }
                                field("Rating: ") { textfield(model.yourRating) }
                                field("Watched on: ") { datepicker(model.watchedAt) }
                                field("Watched at: ") { textfield(model.watchedOn.toString()) }
                                field("Included in import: ") { checkbox(property = model.addToImport) }
                                button("Save") {
                                    enableWhen(model.dirty)
                                    action {
                                        save()
                                    }
                                }
                                button("Reset").action {
                                    model.rollback()
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun save() {
        model.commit()
        val medium = model.medium
        println("Saving ${medium.title}")
    }
}
