package net.emteeware

import java.time.LocalDateTime

data class Media(val imdbId: String, val name : String, val viewDate : LocalDateTime, val type : TraktMediaType, val rating : Int, val ratingDate : LocalDateTime)