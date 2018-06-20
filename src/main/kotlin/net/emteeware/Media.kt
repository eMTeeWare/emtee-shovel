package net.emteeware

import java.time.LocalDateTime

data class Media(
        val imdbId: String,
        val name: String,
        var viewDate: LocalDateTime,
        val type: TraktMediaType,
        val rating: Int,
        val ratingDate: LocalDateTime,
        var runningTimeInMinutes: Int = 0,
        var watchTimeSet: Boolean = false
) : Comparable<Media> {
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