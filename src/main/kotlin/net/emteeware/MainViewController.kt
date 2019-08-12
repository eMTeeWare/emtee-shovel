package net.emteeware

import javafx.beans.property.*
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.stage.FileChooser
import tornadofx.*
import java.io.File
import java.io.UncheckedIOException
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.time.LocalDate
import java.time.Month
import java.time.temporal.ChronoUnit
import java.util.prefs.Preferences

private const val PREFS_LAST_USED_DIR_KEY = "lastUsedDirectory"
private const val HEADER_ROW_COUNT = 1

class MainViewController : Controller() {
    private val prefs = Preferences.userRoot().node(this.javaClass.name)
    val media = FXCollections.observableArrayList<Media>()
    val displayMedia = FXCollections.observableArrayList<Media>()
    val filepreview = SimpleStringProperty("No file selected")
    val importDisabled = SimpleBooleanProperty(true)
    val filename = SimpleStringProperty("No file selected")
    lateinit var file: File
    val initialDirectory = File(prefs[PREFS_LAST_USED_DIR_KEY, System.getProperty("user.home")])
    val filters = arrayOf(FileChooser.ExtensionFilter("CSV files", "*.csv"))
    val lineCountString = SimpleStringProperty("No file selected")
    var separatorString = SimpleStringProperty(";")
    lateinit var listViewStartDate: SimpleObjectProperty<LocalDate>
    lateinit var listViewEndDate: SimpleObjectProperty<LocalDate>
    var firstDay = SimpleDoubleProperty(0.0)
    var lastDay = SimpleDoubleProperty(100.0)

    var coveredDays = SimpleLongProperty(0)

    val mediaDisplayList = SimpleListProperty(displayMedia)

    fun getMediaList(): ObservableList<Media> {
        return media.filtered { m -> m.watchDate.isAfter(listViewStartDate.value.minusDays(1)) }
    }

    private var recognizedCharset = StandardCharsets.UTF_8
    private lateinit var recognizedContentVersion: ContentVersion

    fun importData(file: File) {
        val imdbViewingHistory = ImdbViewingHistory()
        val separatorChar = separatorString.get()[0]
        imdbViewingHistory.importFromCsv(file.canonicalPath, separatorChar, recognizedCharset, recognizedContentVersion)
        listViewStartDate = SimpleObjectProperty(imdbViewingHistory.getMinimumWatchDate())
        listViewStartDate.onChange { applyFilters() }
        listViewEndDate = SimpleObjectProperty(imdbViewingHistory.getMaximumWatchDate())
        coveredDays.set(ChronoUnit.DAYS.between(listViewStartDate.value, listViewEndDate.value))
        lastDay.set(coveredDays.doubleValue())
        println("Loaded file covers ${coveredDays.value} days.")
        media.setAll(imdbViewingHistory.getTraktMediaList())
        displayMedia.setAll(media)
        firstDay.onChange { applyFilters() }
        lastDay.onChange { applyFilters() }
    }

    private fun applyFilters() {
        displayMedia.setAll(media.filtered { m ->
                (m.watchDate.isAfter(listViewStartDate.value.plusDays(firstDay.longValue()).minusDays(1))) &&
                (m.watchDate.isBefore(listViewStartDate.value.plusDays(lastDay.longValue()).minusDays(1)))
        })
    }

    private fun updateInitialDirectory() {
        prefs.put(PREFS_LAST_USED_DIR_KEY, file.path.dropLast(file.name.length))
    }

    fun loadFile(chosenFiles: List<File>) {
        if (chosenFiles.isNotEmpty()) {
            file = chosenFiles[0]
            filename.set(file.name)
            val linecount = try {
                Files.lines(file.toPath()).count()
            } catch (e: UncheckedIOException) {
                recognizedCharset = StandardCharsets.ISO_8859_1
                Files.lines(file.toPath(), recognizedCharset).count()
            } catch (e: Exception) {
                println(e)
                0L
            }
            lineCountString.set("${linecount - HEADER_ROW_COUNT} entries found")
            updateInitialDirectory()
            val fileContentPreview = StringBuffer()
            file.useLines { lines: Sequence<String> ->
                lines
                        .take(3)
                        .forEach { l -> fileContentPreview.append(l).append('\n') }
            }
            filepreview.set(fileContentPreview.toString())
            guessCvsSeparatorChar(fileContentPreview)
            identifyContentVersion(fileContentPreview)
            importDisabled.set(false)
        }
    }

    private fun identifyContentVersion(fileContentPreview: StringBuffer) {
        recognizedContentVersion = if (fileContentPreview.contains("(mins)") && fileContentPreview.contains("\\d\\d\\d\\d-\\d\\d-\\d\\d".toRegex())) {
            ContentVersion.V2
        } else {
            ContentVersion.V1
        }
    }

    private fun guessCvsSeparatorChar(fileContentPreview: StringBuffer) {
        val firstline = fileContentPreview.split('\n')[0]
        if (firstline.contains(',') && !firstline.contains(';')) {
            separatorString.set(",")
        }
    }
}