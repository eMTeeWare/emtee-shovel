package net.emteeware

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import java.net.URL
import java.time.Instant
import java.util.*

const val datepatternV2 = "yyyy-MM-dd"

data class ImportedMediaV2(
        override val Position: Int,
        override val Const: String,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = datepatternV2)
        override val Created: Date,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = datepatternV2)
        val Modified: Date,
        val Description: String,
        override val Title: String,
        override val URL: URL,
        @JsonProperty("Title Type")
        override val TitleType: TraktMediaType,
        @JsonProperty("IMDb Rating")
        val IMDbRating: String,
        @JsonProperty("Runtime (mins)")
        val Runtime: Int,
        val Year: Int,
        val Genres: String,
        @JsonProperty("Num Votes")
        val NumVotes: Int,
        @JsonProperty("Release Date")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = datepatternV2)
        val ReleaseDate: Date,
        val Directors: String,
        @JsonProperty("Your Rating")
        override val YourRating: Int,
        @JsonProperty("Date Rated")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = datepatternV2)
        var DateRated: Date?) : ImportedMedia {
    init {
        try {

            DateRated = DateRated ?: Date.from(Instant.EPOCH)
        } catch (e: ArithmeticException) {
            println(DateRated)
        }
    }
}


