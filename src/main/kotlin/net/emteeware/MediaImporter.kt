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
        try {
            val fileReader = BufferedReader(FileReader(fileToBeImported.path))
            fileReader.readLine()
            val readMedia = fileReader.readLines()
            for (line in readMedia) {
                val mediaData = line.split(';')
                var mediaType = TraktMediaType.UNDEFINED
                if (mediaData[3] == "movie") {
                    mediaType = TraktMediaType.MOVIE
                } else if (mediaData[3] == "tvEpisode") {
                    mediaType = TraktMediaType.EPISODE
                }
                if (mediaData[4] != "") {
                    val watchTime = LocalDateTime.from(LocalDate.parse(mediaData[1], DateTimeFormatter.ofPattern("dd.MM.yyyy")).atStartOfDay())
                    if(watchTime.isBefore(beginningOfTraktCheckIns)) {
                        mediaList += Media(mediaData[0], mediaData[2], watchTime, mediaType, Integer.parseInt(mediaData[4]), LocalDateTime.from(LocalDate.parse(mediaData[5], DateTimeFormatter.ofPattern("dd.MM.yyyy")).atStartOfDay()))
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
