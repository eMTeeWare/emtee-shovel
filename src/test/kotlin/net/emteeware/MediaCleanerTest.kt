package net.emteeware

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.time.LocalDateTime
import java.time.Month

internal class MediaCleanerTest {

    @Test
    fun deduplicateMedia() {
        val mediaCleaner = MediaCleaner()
        val firstMovieStarted = Media("ttid01", "Movie One", LocalDateTime.of(2016, Month.MARCH, 16, 10, 0), TraktMediaType.MOVIE, 8, LocalDateTime.of(2016, Month.JUNE, 16, 12, 0), 100)
        val secondMovieViewed = Media("ttid02", "Movie Two", LocalDateTime.of(2016, Month.MARCH, 17, 10, 0), TraktMediaType.MOVIE, 8, LocalDateTime.of(2016, Month.MARCH, 17, 12, 0), 100)
        val firstMovieContinued = Media("ttid01", "Movie One", LocalDateTime.of(2016, Month.MARCH, 18, 10, 0), TraktMediaType.MOVIE, 8, LocalDateTime.of(2016, Month.JUNE, 16, 12, 0), 100)
        val thirdMovieViewed = Media("ttid03", "Movie Three", LocalDateTime.of(2016, Month.MARCH, 20, 10, 0), TraktMediaType.MOVIE, 8, LocalDateTime.of(2016, Month.MARCH, 20, 12, 0), 100)
        val firstMovieRewatched = Media("ttid01", "Movie One", LocalDateTime.of(2016, Month.JUNE, 16, 10, 0), TraktMediaType.MOVIE, 8, LocalDateTime.of(2016, Month.JUNE, 16, 12, 0), 100)
        val expectedMediaList = listOf(secondMovieViewed, firstMovieContinued, thirdMovieViewed, firstMovieRewatched)
        val actualMediaList = mediaCleaner.deduplicateMedia(listOf(firstMovieContinued, firstMovieRewatched, firstMovieStarted, secondMovieViewed, thirdMovieViewed), 10)

        assertTrue(expectedMediaList.sorted() == actualMediaList.sorted())
    }

    @Test
    fun removeUnsupportedMedia() {
        val mediaCleaner = MediaCleaner()
        val validMedia = Media("ttid01", "Movie One", LocalDateTime.of(2016, Month.MARCH, 16, 10, 0), TraktMediaType.MOVIE, 8, LocalDateTime.of(2016, Month.JUNE, 16, 12, 0), 100)
        val invalidMedia = Media("ttid02", "Movie Two", LocalDateTime.of(2016, Month.MARCH, 17, 10, 0), TraktMediaType.UNDEFINED, 8, LocalDateTime.of(2016, Month.MARCH, 17, 12, 0), 100)

        val expectedMediaList = listOf(validMedia)
        val actualMediaList = mediaCleaner.removeUnsupportedMedia(listOf(validMedia, invalidMedia))

        assertTrue(expectedMediaList.sorted() == actualMediaList.sorted())
    }


}