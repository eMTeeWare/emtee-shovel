package net.emteeware

import java.time.LocalDateTime
import java.time.ZoneId

data class Media(
        val imdbId: String,
        val name: String,
        var viewDate: LocalDateTime,
        val type: TraktMediaType,
        val rating: Int,
        val ratingDate: LocalDateTime,
        private var runningTimeInMinutes: Int = 0,
        var watchTimeSet: Boolean = false
) : Comparable<Media> {
    constructor(importedMedia: ImportedMedia) :
            this(
                    imdbId = importedMedia.Const,
                    name = importedMedia.Title,
                    viewDate = importedMedia.Created.toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDateTime(),
                    type = importedMedia.TitleType,
                    rating = importedMedia.YourRating,
                    ratingDate = importedMedia.DateRated!!.toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDateTime(),
                    runningTimeInMinutes = importedMedia.Runtime
                    )

    init {
        if (runningTimeInMinutes == 0) {
            runningTimeInMinutes = when (type) {
                TraktMediaType.MOVIE -> 125
                TraktMediaType.EPISODE -> 45
                TraktMediaType.UNDEFINED -> 0
            }
        }
    }

    override fun compareTo(other: Media): Int {
        return if (imdbId == other.imdbId) {
            viewDate.compareTo(other.viewDate)
        } else {
            imdbId.compareTo(other.imdbId)
        }
    }
}