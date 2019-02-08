package net.emteeware

import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import java.net.URL
import java.time.LocalDate
import java.time.LocalTime
import tornadofx.*

class Media(position: Int  = 0,
            imdbId: String = "",
            watchedAt: LocalDate = LocalDate.MIN,
            title: String = "",
            imdbUrl: URL = URL("http://127.0.0.1"),
            mediaType: TraktMediaType = TraktMediaType.UNDEFINED,
            yourRating: Int = 0,
            addToImport: Boolean = true,
            watcheOn: LocalTime = LocalTime.MIDNIGHT,
            watchTimeSet: Boolean = false) {
    val positionProperty = SimpleIntegerProperty(this, "position", position)
    var position by positionProperty

    val watchedAtProperty = SimpleObjectProperty<LocalDate>(this, "watchedAt", watchedAt)
    var watchedAt by watchedAtProperty

    val titleProperty = SimpleStringProperty(this, "title", title)
    var title by titleProperty

    val imdbIdProperty = SimpleStringProperty(this, "imdbId", imdbId)
    var imdbId by imdbIdProperty

    val imdbUrlProperty = SimpleObjectProperty<URL>(this, "imdbUrl", imdbUrl)
    var imdbUrl by imdbUrlProperty

    val mediaTypeProperty = SimpleObjectProperty<TraktMediaType>(this, "mediaType", mediaType)
    var mediaType by mediaTypeProperty

    val yourRatingProperty = SimpleIntegerProperty(this, "yourRating", yourRating)
    var yourRating by yourRatingProperty

    val addToImportProperty = SimpleBooleanProperty(this, "addToImport", addToImport)
    var addToImport by addToImportProperty

    val watchedOnProperty = SimpleObjectProperty<LocalTime>(this, "watchedOn", watcheOn)
    var watcheOn by watchedOnProperty

    val watchTimeSetProperty = SimpleBooleanProperty(this, "watchTimeSet", watchTimeSet)
    var watchTimeSet by watchTimeSetProperty

    override fun toString(): String {
        return title
    }


}
