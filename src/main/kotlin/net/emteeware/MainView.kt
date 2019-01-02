package net.emteeware

import javafx.collections.FXCollections
import javafx.scene.Node
import tornadofx.*
import java.net.URL
import java.time.LocalDate

class MainView : View("My View") {
    override val root = borderpane {
        center = centerVBox()
    }

    private fun centerVBox(): Node {
        return vbox {
            val media = FXCollections.observableArrayList(
                    Media(1, "kk2342", LocalDate.of(2018, 4, 21), "asdfas", URL("https://www.example.com/kk2342"), TraktMediaType.EPISODE, 8),
                    Media(8, "kk2382", LocalDate.of(2018, 2, 21), "sda5dhf", URL("https://www.example.com/kk2382"), TraktMediaType.MOVIE, 3),
                    Media(9, "kk2392", LocalDate.of(2018, 6, 21), "asd45", URL("https://www.example.com/kk2392"), TraktMediaType.EPISODE, 7)
            )
            tableview(media) {
                readonlyColumn("Date", Media::watchedAt)
                readonlyColumn("Title", Media::title)
                readonlyColumn("Type", Media::mediaType)
                columnResizePolicy = SmartResize.POLICY
            }
        }
    }
}
