package net.emteeware

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.time.LocalDateTime
import java.time.Month

internal class MediaListCleaningTest {

    @Test
    fun deduplicateMedia() {

        val firstMovieStarted = Media("ttid01", "Movie One", LocalDateTime.of(2016, Month.MARCH, 16, 10, 0), TraktMediaType.MOVIE, 8, LocalDateTime.of(2016, Month.JUNE, 16, 12, 0), 100)
        val secondMovieViewed = Media("ttid02", "Movie Two", LocalDateTime.of(2016, Month.MARCH, 17, 10, 0), TraktMediaType.MOVIE, 8, LocalDateTime.of(2016, Month.MARCH, 17, 12, 0), 100)
        val firstMovieContinued = Media("ttid01", "Movie One", LocalDateTime.of(2016, Month.MARCH, 18, 10, 0), TraktMediaType.MOVIE, 8, LocalDateTime.of(2016, Month.JUNE, 16, 12, 0), 100)
        val thirdMovieViewed = Media("ttid03", "Movie Three", LocalDateTime.of(2016, Month.MARCH, 20, 10, 0), TraktMediaType.MOVIE, 8, LocalDateTime.of(2016, Month.MARCH, 20, 12, 0), 100)
        val firstMovieRewatched = Media("ttid01", "Movie One", LocalDateTime.of(2016, Month.JUNE, 16, 10, 0), TraktMediaType.MOVIE, 8, LocalDateTime.of(2016, Month.JUNE, 16, 12, 0), 100)
        val expectedMediaList = MediaList(listOf(secondMovieViewed, firstMovieContinued, thirdMovieViewed, firstMovieRewatched))
        val actualMediaList = MediaList(listOf(firstMovieContinued, firstMovieRewatched, firstMovieStarted, secondMovieViewed, thirdMovieViewed))
        actualMediaList.deduplicate(14)

        assertTrue(expectedMediaList.sorted() == actualMediaList.sorted())
    }

    @Test
    fun removeUnsupportedMedia() {
        val validMedia = Media("ttid01", "Movie One", LocalDateTime.of(2016, Month.MARCH, 16, 10, 0), TraktMediaType.MOVIE, 8, LocalDateTime.of(2016, Month.JUNE, 16, 12, 0), 100)
        val invalidMedia = Media("ttid02", "Movie Two", LocalDateTime.of(2016, Month.MARCH, 17, 10, 0), TraktMediaType.UNDEFINED, 8, LocalDateTime.of(2016, Month.MARCH, 17, 12, 0), 100)

        val expectedMediaList = MediaList(listOf(validMedia))
        val actualMediaList = MediaList(listOf(validMedia, invalidMedia))
        actualMediaList.removeUnsupportedMedia()

        assertTrue(expectedMediaList.sorted() == actualMediaList.sorted())
    }


}