package net.emteeware

import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.time.Month
import java.util.*
import kotlin.test.assertTrue

internal class MediaImporterTest {

    @Test
    fun importMediaListWithUnratedMovieDropped() {
        val mediaImporter = MediaImporter()
        val expectedList = ArrayList<Media>()
        expectedList += Media("tt2473822",
                "Elementary: Flight Risk",
                LocalDateTime.of(2014, Month.JANUARY, 18, 0, 0),
                TraktMediaType.EPISODE,
                7,
                LocalDateTime.of(2014, Month.JANUARY, 18, 0, 0),
                45
        )
        expectedList += Media("tt2481598",
                "Elementary: One Way to Get Off",
                LocalDateTime.of(2014, Month.JANUARY, 18, 0, 0),
                TraktMediaType.EPISODE,
                8,
                LocalDateTime.of(2014, Month.JANUARY, 19, 0, 0),
                45
        )
        expectedList += Media("tt0070608",
                "Robin Hood",
                LocalDateTime.of(2014, Month.MARCH, 15, 0, 0),
                TraktMediaType.MOVIE,
                8,
                LocalDateTime.of(2014, Month.MARCH, 15, 0, 0),
                80
        )
        expectedList += Media("tt1229238",
                "Mission: Impossible - Ghost Protocol",
                LocalDateTime.of(2014, Month.MARCH, 17, 0, 0),
                TraktMediaType.MOVIE,
                7,
                LocalDateTime.of(2014, Month.MARCH, 17, 0, 0),
                125
        )

        val importFile = this::class.java.classLoader.getResource("SampleMoviesWithUnratedMedia.csv").toURI()
        val importUntil = LocalDateTime.of(2018, Month.APRIL, 21, 22, 4)
        val actualList = mediaImporter.importMediaList(importFile, importUntil)
        assertTrue(expectedList.sorted() == actualList.sorted())

    }

    @Test
    fun importMediaListWithAlreadyCheckedInMediaDropped() {
        val mediaImporter = MediaImporter()
        val expectedList = ArrayList<Media>()
        expectedList += Media("tt2473822",
                "Elementary: Flight Risk",
                LocalDateTime.of(2014, Month.JANUARY, 18, 0, 0),
                TraktMediaType.EPISODE,
                7,
                LocalDateTime.of(2014, Month.JANUARY, 18, 0, 0),
                45
        )
        expectedList += Media("tt2481598",
                "Elementary: One Way to Get Off",
                LocalDateTime.of(2014, Month.JANUARY, 18, 0, 0),
                TraktMediaType.EPISODE,
                8,
                LocalDateTime.of(2014, Month.JANUARY, 19, 0, 0),
                45
        )
        expectedList += Media("tt0070608",
                "Robin Hood",
                LocalDateTime.of(2014, Month.MARCH, 15, 0, 0),
                TraktMediaType.MOVIE,
                8,
                LocalDateTime.of(2014, Month.MARCH, 15, 0, 0),
                80
        )

        val importFile = this::class.java.classLoader.getResource("SampleMovies.csv").toURI()
        val importUntil = LocalDateTime.of(2014, Month.MARCH, 16, 22, 4)
        val actualList = mediaImporter.importMediaList(importFile, importUntil)

        assertTrue(expectedList.sorted() == actualList.sorted())

    }
}