package net.emteeware

import javafx.collections.FXCollections
import javafx.event.EventHandler
import javafx.scene.Node
import javafx.scene.control.Button
import tornadofx.*
import java.net.URL
import java.time.LocalDate

class MainView : View("My View") {

    private var media = FXCollections.observableArrayList(
            Media(1, "kk2342", LocalDate.of(2018, 4, 21), "asdfas", URL("https://www.example.com/kk2342"), TraktMediaType.EPISODE, 8),
            Media(8, "kk2382", LocalDate.of(2018, 2, 21), "sda5dhf", URL("https://www.example.com/kk2382"), TraktMediaType.MOVIE, 3),
            Media(9, "kk2392", LocalDate.of(2018, 6, 21), "asd45", URL("https://www.example.com/kk2392"), TraktMediaType.EPISODE, 7)
    )
    override val root = borderpane {
        top = menuBox()
        center = centerVBox()
    }

    private fun menuBox(): Node {
        return vbox {
            button("import data").apply {
                onAction = EventHandler { importData() }
            }
        }

    }

    private fun importData() {
        val imdbViewingHistory = ImdbViewingHistory()
        imdbViewingHistory.importFromCsv("E:\\Archive\\Medienzeugs\\trakt import list full.csv")
        media.setAll(imdbViewingHistory.getTraktMediaList())
        println("media loaded")
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
