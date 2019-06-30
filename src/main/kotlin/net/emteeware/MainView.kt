package net.emteeware

import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleDoubleProperty
import javafx.event.EventHandler
import javafx.geometry.Orientation
import javafx.scene.layout.BorderPane
import javafx.scene.paint.Color
import org.controlsfx.control.RangeSlider
import tornadofx.*
import tornadofx.controlsfx.rangeslider
import java.sql.Timestamp
import java.time.LocalDateTime
import java.time.ZoneOffset


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
            var lowValue = SimpleDoubleProperty(0.0)
            var highValue = SimpleDoubleProperty((Timestamp.valueOf(LocalDateTime.now(ZoneOffset.UTC)).nanos.toDouble()))

            var rangeSlider by singleAssign<RangeSlider>()
            center {
                vbox {
                    pane {
                        rangeslider(lowValue.value, highValue.value, 0.0, 1.0) {
                            useMaxWidth = true
                            isShowTickMarks = true
                            isShowTickLabels = true

                        }
                        style {
                            backgroundColor += Color.RED
                        }
                    }
                    hbox {
                        tableview(media) {
                            column("Date", Media::watchDateProperty)
                            column("Title", Media::titleProperty)
                            column("Type", Media::mediaTypeProperty)
                            column("Rating", Media::yourRatingProperty)
                            columnResizePolicy = SmartResize.POLICY
                            model.rebindOnChange(this) { selectedMedia ->
                                medium = selectedMedia ?: Media()
                                println("rebindOnChange: $medium")
                            }
                            onSelectionChange { selectedMedia -> println("onChange: $selectedMedia") }
                        }
                        form {
                            fieldset("Selected media") {
                                field("Title: ") { textfield(model.title) }
                                field("Type: ") { combobox(model.mediaType, TraktMediaType.values().toList()) }
                                field("Rating: ") { textfield(model.yourRating) }
                                field("Watched on: ") { datepicker(model.watchDate) }
                                field("Watched at: ") {
                                    hbox {
                                        spinner(0, 23, property = model.watchHour, editable = true) { prefWidth = 60.0 }
                                        spinner(0, 59, property = model.watchMinute, editable = true) { prefWidth = 60.0 }
                                    }
                                }
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
