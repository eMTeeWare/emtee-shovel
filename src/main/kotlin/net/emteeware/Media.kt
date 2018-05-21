package net.emteeware

import java.time.LocalDateTime

data class Media(val imdbId: String, val name : String, val viewDate : LocalDateTime, val type : TraktMediaType, val rating : Int, val ratingDate : LocalDateTime) : Comparable<Media> {
    override fun compareTo(other: Media): Int {
        if(imdbId == other.imdbId) {
            return viewDate.compareTo(other.viewDate)
        } else {
            return imdbId.compareTo(other.imdbId)
        }
    }
}