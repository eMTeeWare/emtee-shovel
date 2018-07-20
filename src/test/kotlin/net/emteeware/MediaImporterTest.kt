package net.emteeware

import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.time.Month
import java.util.*

internal class MediaImporterTest {

    @Test
    fun executeImport() {
        val mediaImporter = MediaImporter()
        val expectedList = ArrayList<Media>()
        expectedList += Media("tt0000000",
                "TvShow: Episode - Watched and Rated on Same Day",
                LocalDateTime.of(2014, Month.JANUARY, 18, 0, 0),
                TraktMediaType.EPISODE,
                7,
                LocalDateTime.of(2014, Month.JANUARY, 18, 0, 0),
                45
        )
        expectedList += Media("tt0000001",
                "TvShow: Episode - Watched and Rated on Different Days",
                LocalDateTime.of(2014, Month.JANUARY, 18, 0, 0),
                TraktMediaType.EPISODE,
                8,
                LocalDateTime.of(2014, Month.JANUARY, 19, 0, 0),
                45
        )
        expectedList += Media("tt0000002",
                "Movie - Watched and Rated on Same Day",
                LocalDateTime.of(2014, Month.MARCH, 15, 0, 0),
                TraktMediaType.MOVIE,
                8,
                LocalDateTime.of(2014, Month.MARCH, 15, 0, 0),
                80
        )
        expectedList += Media("tt0000003",
                "Movie - Watched and Rated on Different Days",
                LocalDateTime.of(2014, Month.MARCH, 17, 0, 0),
                TraktMediaType.MOVIE,
                7,
                LocalDateTime.of(2014, Month.MARCH, 18, 0, 0),
                125)
        expectedList += Media("tt0000006",
                "TvShow: Episode - Watched in two parts - Must be combined",
                LocalDateTime.of(2017, Month.JANUARY, 2, 0, 0),
                TraktMediaType.EPISODE,
                10,
                LocalDateTime.of(2017, Month.JANUARY, 2, 0, 0),
                40)
        expectedList += Media("tt0000007",
                "Movie - Watched in two parts - Must be combined",
                LocalDateTime.of(2017, Month.JANUARY, 2, 0, 0),
                TraktMediaType.MOVIE,
                10,
                LocalDateTime.of(2017, Month.JANUARY, 2, 0, 0),
                120)
        expectedList += Media("tt0000007",
                "Movie - Rewatch - Must appear",
                LocalDateTime.of(2018, Month.JANUARY, 2, 0, 0),
                TraktMediaType.MOVIE,
                10,
                LocalDateTime.of(2017, Month.JANUARY, 2, 0, 0),
                120)
        expectedList += Media("tt0000006",
                "TvShow: Episode - Rewatch - Must appear",
                LocalDateTime.of(2018, Month.JANUARY, 2, 0, 0),
                TraktMediaType.EPISODE,
                10,
                LocalDateTime.of(2017, Month.JANUARY, 2, 0, 0),
                40)
        expectedList += Media("tt0000011",
                "TvShow: Episode - without runtime - must be set to 45",
                LocalDateTime.of(2018, Month.JANUARY, 2, 0, 0),
                TraktMediaType.EPISODE,
                5,
                LocalDateTime.of(2018, Month.JANUARY, 2, 0, 0),
                45)
        expectedList += Media("tt0000012",
                "Movie - without runtime - must be set to 125",
                LocalDateTime.of(2018, Month.JANUARY, 2, 0, 0),
                TraktMediaType.MOVIE,
                5,
                LocalDateTime.of(2018, Month.JANUARY, 2, 0, 0),
                125)
        val importFile = this::class.java.classLoader.getResource("SampleMoviesForMainImporterTest.csv").toString()
        mediaImporter.executeImport(arrayOf(importFile))
        val actualList = mediaImporter.importMediaList
        kotlin.test.assertTrue(expectedList.sorted() == actualList.sorted(), "Expected: $expectedList., received: $actualList")
    }
}