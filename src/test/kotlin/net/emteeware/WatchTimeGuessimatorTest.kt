package net.emteeware

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.io.ByteArrayInputStream
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Month

internal class WatchTimeGuessimatorTest {

    @Test
    fun askUserByDate() {
        val watchHour = 17
        val outOfScopeMovie = Media("ttoos", "Out of Scope", LocalDateTime.of(2004, Month.APRIL, 1, 0, 0), TraktMediaType.MOVIE, 3, LocalDateTime.of(2004, Month.APRIL, 1, 0, 0))
        val inScopeMovie = Media("tts", "Out of Scope", LocalDateTime.of(2014, Month.APRIL, 1, 0, 0), TraktMediaType.MOVIE, 3, LocalDateTime.of(2014, Month.APRIL, 1, 0, 0))
        val inScopeMovieUpdated = Media("tts", "Out of Scope", LocalDateTime.of(2014, Month.APRIL, 1, watchHour, 0), TraktMediaType.MOVIE, 3, LocalDateTime.of(2014, Month.APRIL, 1, 0, 0), true)
        val watchTimeGuessimator = WatchTimeGuessimator(listOf(outOfScopeMovie, inScopeMovie))

        val inContent = watchHour.toString()
        val inputStream = ByteArrayInputStream(inContent.toByteArray())

        System.setIn(inputStream)

        watchTimeGuessimator.askUserByDate(LocalDate.of(2012, Month.APRIL, 1), LocalDate.of(2016, Month.APRIL, 1))

        System.setIn(System.`in`)

        assertTrue(watchTimeGuessimator.mediaList.sorted() == listOf(outOfScopeMovie, inScopeMovieUpdated).sorted())
    }
    @Test
    fun askUserByType() {
        val watchHour = 17
        val episode = Media("ttepisode", "Episode", LocalDateTime.of(2004, Month.APRIL, 1, 0, 0), TraktMediaType.EPISODE, 3, LocalDateTime.of(2004, Month.APRIL, 1, 0, 0))
        val movie = Media("ttmovie", "Movie", LocalDateTime.of(2014, Month.APRIL, 1, 0, 0), TraktMediaType.MOVIE, 3, LocalDateTime.of(2014, Month.APRIL, 1, 0, 0))
        val movieUpdated = Media("ttmovie", "Movie", LocalDateTime.of(2014, Month.APRIL, 1, watchHour, 0), TraktMediaType.MOVIE, 3, LocalDateTime.of(2014, Month.APRIL, 1, 0, 0), true)
        val watchTimeGuessimator = WatchTimeGuessimator(listOf(movie, episode))

        val inContent = watchHour.toString()
        val inputStream = ByteArrayInputStream(inContent.toByteArray())

        System.setIn(inputStream)

        watchTimeGuessimator.askUserByType(TraktMediaType.MOVIE)

        System.setIn(System.`in`)

        assertTrue(watchTimeGuessimator.mediaList.sorted() == listOf(episode, movieUpdated).sorted())
    }
}