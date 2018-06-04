package net.emteeware

import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.time.Month
import kotlin.test.assertEquals

internal class MediaTest {

    @Test
    fun `media creation`() {
        val testMovie = Media("tt2473822", "Elementary: Flight Risk", LocalDateTime.of(2014, Month.JANUARY, 18, 22, 15), TraktMediaType.EPISODE, 7, LocalDateTime.of(2014, Month.JANUARY, 18, 22, 15), 45)
        assertEquals("Media(imdbId=tt2473822, name=Elementary: Flight Risk, viewDate=2014-01-18T22:15, type=EPISODE, rating=7, ratingDate=2014-01-18T22:15, runningTimeInMinutes=45, watchTimeSet=false)", testMovie.toString())
    }
}