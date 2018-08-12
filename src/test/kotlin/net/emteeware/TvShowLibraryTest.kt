package net.emteeware

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import java.time.LocalDateTime

internal class TvShowLibraryTest {



    @Test
    fun printShows() {

        val seriesOneEpisodeOne = Media("ttid01", "Series One: Episode One", LocalDateTime.now(), TraktMediaType.EPISODE, 8, LocalDateTime.now(), 50)
        val seriesOneEpisodeTwo = Media("ttid02", "Series One: Episode Two", LocalDateTime.now(), TraktMediaType.EPISODE, 8, LocalDateTime.now(), 50)
        val seriesTwoEpisodeOne = Media("ttid03", "Series Two: Episode One", LocalDateTime.now(), TraktMediaType.EPISODE, 8, LocalDateTime.now(), 50)
        val seriesTwoEpisodeTwo = Media("ttid05", "Series Two: Episode Two", LocalDateTime.now(), TraktMediaType.EPISODE, 8, LocalDateTime.now(), 50)

        val outputStream = ByteArrayOutputStream()
        System.setOut(PrintStream(outputStream))
        TvShowLibrary(MediaList(listOf(seriesOneEpisodeOne, seriesTwoEpisodeOne, seriesTwoEpisodeTwo, seriesOneEpisodeTwo))).printShows()
        assertEquals("Series One=TvShow(name=Series One, defaultWatchTime=00:00)" + System.getProperty("line.separator") +
                "Series Two=TvShow(name=Series Two, defaultWatchTime=00:00)" + System.getProperty("line.separator"), outputStream.toString())
        System.setOut(System.out)
    }
}