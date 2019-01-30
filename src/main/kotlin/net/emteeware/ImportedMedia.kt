package net.emteeware

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import java.net.URL
import java.time.Instant
import java.util.*

const val datepattern = "dd.MM.yyyy"

data class ImportedMedia(
        val Position: Int,
        val Const: String,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = datepattern)
        val Created: Date,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = datepattern)
        val Modified: Date,
        val Description: String,
        val Title: String,
        val URL: URL,
        @JsonProperty("Title Type")
        val TitleType: TraktMediaType,
        @JsonProperty("IMDb Rating")
        val IMDbRating: String,
        val Runtime: Int,
        val Year: Int,
        val Genres: String,
        @JsonProperty("Num Votes")
        val NumVotes: Int,
        @JsonProperty("Release Date")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = datepattern)
        val ReleaseDate: Date,
        val Directors: String,
        @JsonProperty("Your Rating")
        val YourRating: Int,
        @JsonProperty("Date Rated")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = datepattern)
        var DateRated: Date?) {
    init {
        try {

            DateRated = DateRated ?: Date.from(Instant.EPOCH)
        } catch (e: ArithmeticException) {
            println(DateRated)
        }
    }
}


