package net.emteeware

import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVRecord
import java.io.FileReader
import java.net.URI
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class CsvImporter {
    fun importMediaList(fileToBeImported: URI, beginningOfTraktCheckIns: LocalDateTime): List<Media> {
        val mediaList = ArrayList<Media>()
        var droppedUnratedMediaCounter = 0
        var droppedAlreadyCheckedInMediaCounter = 0

        try {
            val inReader = FileReader(fileToBeImported.path)
            val records : Iterable<CSVRecord> = CSVFormat.EXCEL.withDelimiter(';').withFirstRecordAsHeader().parse(inReader)
            for(record in records) {
                // TODO: this shit has to be refactored out to a mediaCollection container, but is here for the moment to make the tests run
                when {
                    LocalDateTime.from(LocalDate.parse(record.get("Created"), DateTimeFormatter.ofPattern("dd.MM.yyyy")).atStartOfDay()).isAfter(beginningOfTraktCheckIns) -> droppedAlreadyCheckedInMediaCounter++
                    record.get("Your Rating") == "" -> droppedUnratedMediaCounter++
                    else -> {
                        mediaList.add(Media(
                                record.get("Const"),
                                record.get("Title"),
                                record.get("Created").asDateTime,
                                record.get("Title Type").asMediaType,
                                record.get("Your Rating").asIntWithDefault(0),
                                record.get("Date Rated").asDateTime,
                                record.get("Runtime").asIntWithDefault(0),
                                false
                        ))
                    }
                }
            }
            println("$droppedUnratedMediaCounter unrated media dropped during import")
            println("$droppedAlreadyCheckedInMediaCounter media dropped because they were in a timeframe already checked in to trakt")
        } catch (e: Exception) {
            println("An error occurred while reading csv file: ${e.localizedMessage}")
        }
        return mediaList
    }

}

private fun String?.asIntWithDefault(default: Int): Int {
    return try {
        this?.toInt() ?: default
    } catch (e: NumberFormatException) {
        default
    }
}

private val String.asMediaType: TraktMediaType
    get() {
        return when(this) {
            "movie" -> TraktMediaType.MOVIE
            "tvEpisode" -> TraktMediaType.EPISODE
            else -> TraktMediaType.UNDEFINED
        }
    }

private val String.asDateTime: LocalDateTime
    get() {
        return LocalDateTime.from(LocalDate.parse(this, DateTimeFormatter.ofPattern("dd.MM.yyyy")).atStartOfDay())
    }