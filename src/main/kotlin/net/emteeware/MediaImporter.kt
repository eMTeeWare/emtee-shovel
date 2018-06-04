package net.emteeware

import java.io.BufferedReader
import java.io.FileReader
import java.net.URI
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MediaImporter {
    fun importMediaList(fileToBeImported: URI, beginningOfTraktCheckIns: LocalDateTime): List<Media> {
        val mediaList = ArrayList<Media>()
        var droppedUnratedMediaCounter = 0
        var droppedAlreadyCheckedInMediaCounter = 0
        val MEDIA_IMDB_ID_COLUMN = 0
        val MEDIA_WATCH_DATE_COLUMN = 1
        val MEDIA_TITLE_COLUMN = 2
        val MEDIA_TYPE_COLUMN = 3
        val MEDIA_RATING_COLUMN = 4
        val MEDIA_RATING_DATE_COLUMN = 5
        val MEDIA_RUNTIME_COLUMN = 6
        try {
            val fileReader = BufferedReader(FileReader(fileToBeImported.path))
            fileReader.readLine()
            val readMedia = fileReader.readLines()
            for (line in readMedia) {
                val mediaData = line.split(';')
                var mediaType = TraktMediaType.UNDEFINED
                if (mediaData[MEDIA_TYPE_COLUMN] == "movie") {
                    mediaType = TraktMediaType.MOVIE
                } else if (mediaData[MEDIA_TYPE_COLUMN] == "tvEpisode") {
                    mediaType = TraktMediaType.EPISODE
                }
                if (mediaData[MEDIA_RATING_COLUMN] != "") {
                    val watchTime = LocalDateTime.from(LocalDate.parse(mediaData[MEDIA_WATCH_DATE_COLUMN], DateTimeFormatter.ofPattern("dd.MM.yyyy")).atStartOfDay())
                    if(watchTime.isBefore(beginningOfTraktCheckIns)) {
                        mediaList += Media(
                                mediaData[MEDIA_IMDB_ID_COLUMN],
                                mediaData[MEDIA_TITLE_COLUMN],
                                watchTime,
                                mediaType,
                                Integer.parseInt(mediaData[MEDIA_RATING_COLUMN]),
                                LocalDateTime.from(LocalDate.parse(mediaData[MEDIA_RATING_DATE_COLUMN], DateTimeFormatter.ofPattern("dd.MM.yyyy")).atStartOfDay()),
                                Integer.parseInt(mediaData[MEDIA_RUNTIME_COLUMN])
                        )
                    } else {
                        droppedAlreadyCheckedInMediaCounter++
                    }
                } else {
                    droppedUnratedMediaCounter++
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
}
