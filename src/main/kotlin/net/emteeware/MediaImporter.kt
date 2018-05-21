package net.emteeware

import java.io.BufferedReader
import java.io.FileReader
import java.net.URI
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MediaImporter {
    fun importMediaList(fileToBeImported: URI): List<Media> {
        val mediaList = ArrayList<Media>()
        var dropCounter = 0
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
                    mediaList += Media(mediaData[0], mediaData[2], LocalDateTime.from(LocalDate.parse(mediaData[1], DateTimeFormatter.ofPattern("dd.MM.yyyy")).atStartOfDay()), mediaType, Integer.parseInt(mediaData[4]), LocalDateTime.from(LocalDate.parse(mediaData[5], DateTimeFormatter.ofPattern("dd.MM.yyyy")).atStartOfDay()))
                } else {
                    dropCounter++
                }
            }
            println("$dropCounter unrated media dropped during import")
        } catch (e: Exception) {
            val message = e.localizedMessage
            println("An error occurred while reading csv file: $message")
        }
        return mediaList
    }
}
