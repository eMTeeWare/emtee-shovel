package net.emteeware

import java.io.BufferedReader
import java.io.FileReader
import java.net.URL
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MediaImporter {
    fun importMediaList(fileToBeImported: URL): List<Media> {
        val mediaList = ArrayList<Media>()
        try {
            val fileReader = BufferedReader(FileReader(fileToBeImported.file))
            fileReader.readLine()
            val readMedia = fileReader.readLines()
            for(line in readMedia) {
                val mediaData = line.split(';')
                var mediaType = TraktMediaType.UNDEFINED
                if(mediaData[3] == "movie"){
                    mediaType = TraktMediaType.MOVIE
                } else if (mediaData[3] == "tvEpisode") {
                    mediaType = TraktMediaType.EPISODE
                }
                mediaList += Media(mediaData[0], mediaData[2], LocalDateTime.from(LocalDate.parse(mediaData[1], DateTimeFormatter.ofPattern("dd.MM.yyyy")).atStartOfDay()), mediaType, Integer.parseInt(mediaData[4]), LocalDateTime.from(LocalDate.parse(mediaData[5], DateTimeFormatter.ofPattern("dd.MM.yyyy")).atStartOfDay()))
            }

        } catch (e: Exception) {
            val message = e.localizedMessage
            println("An error occurred while reading csv file: $message")
        }
        return mediaList
    }
}
