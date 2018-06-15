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
                                parseMediaType(record.get("Title Type")),
                                Integer.parseInt(record.get("Your Rating")),
                                record.get("Date Rated").asDateTime,
                                parseStringToIntWithDefaultValue(record.get("Runtime")),
                                false
                        ))
                    }
                }
            }
            println("$droppedUnratedMediaCounter unrated media dropped during import")
            println("$droppedAlreadyCheckedInMediaCounter media dropped because they were in a timeframe already checked in to trakt")
        } catch (e: Exception) {
            val message = e.localizedMessage
            println("An error occurred while reading csv file: $message")
        }
        return mediaList
    }

    private fun parseStringToIntWithDefaultValue(input: String?): Int {
        return try {
            input?.toInt() ?: 0
        } catch (e: NumberFormatException) {
            0
        }
    }

    private fun parseMediaType(imdbMediaType: String?): TraktMediaType {
        if(imdbMediaType == "movie") return TraktMediaType.MOVIE
        if(imdbMediaType == "tvEpisode") return TraktMediaType.EPISODE
        return TraktMediaType.UNDEFINED
    }
}

private val String.asDateTime: LocalDateTime
    get() {
        return LocalDateTime.from(LocalDate.parse(this, DateTimeFormatter.ofPattern("dd.MM.yyyy")).atStartOfDay())
    }