package net.emteeware

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.MappingIterator
import com.fasterxml.jackson.dataformat.csv.CsvMapper
import com.fasterxml.jackson.dataformat.csv.CsvSchema
import com.fasterxml.jackson.module.kotlin.KotlinModule
import java.io.File
import java.net.URL
import java.time.Instant
import java.util.*

data class ImportedMedia(
        val Position: Int,
        val Const: String,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
        val Created: Date,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
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
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
        val ReleaseDate: Date,
        val Directors: String,
        @JsonProperty("Your Rating")
        val YourRating: Int,
        @JsonProperty("Date Rated")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
        var DateRated: Date?) {
    init {
        try {

            DateRated = DateRated ?: Date.from(Instant.EPOCH)
        } catch (e: ArithmeticException) {
            println(DateRated)
        }
    }
}


inline fun <reified T> readCsv(filename: String): List<T> {
    // This method is adopted from a solution of user Xaxxus ( https://stackoverflow.com/users/9768031/xaxxus )
    // on Stack Overflow: https://stackoverflow.com/a/50278646
    // used under cc-by-sa license https://creativecommons.org/licenses/by-sa/3.0/

    val mapper = CsvMapper().apply { registerModule(KotlinModule()) }
    mapper.enable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE)
    val bootstrapSchema = CsvSchema.emptySchema().withHeader().withColumnSeparator(';')
    val objectReader = mapper.readerFor(T::class.java).with(bootstrapSchema)


    File(filename).inputStream().use { fileInputStream ->
        val mappingIterator: MappingIterator<T> = objectReader.readValues(fileInputStream)

    val items = mutableListOf<T>()

    mappingIterator.forEach { items.add(it) }

    return items.toList()
    }
}