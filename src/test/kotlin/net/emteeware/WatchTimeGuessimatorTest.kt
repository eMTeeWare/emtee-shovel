package net.emteeware

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.time.LocalDateTime
import java.time.Month

internal class WatchTimeGuessimatorTest {

    @Test
    fun askUserByDate() {
        val watchTimeGuessimator = WatchTimeGuessimator()
        val watchHour = 17
        val outOfScopeMovie = Media("ttoos", "Out of Scope", LocalDateTime.of(2004, Month.APRIL, 1, 0, 0), TraktMediaType.MOVIE, 3, LocalDateTime.of(2004, Month.APRIL, 1, 0, 0))
        val inScopeMovie = Media("tts", "Out of Scope", LocalDateTime.of(2014, Month.APRIL, 1, 0, 0), TraktMediaType.MOVIE, 3, LocalDateTime.of(2014, Month.APRIL, 1, 0, 0))
        val inScopeMovieUpdated = Media("tts", "Out of Scope", LocalDateTime.of(2014, Month.APRIL, 1, watchHour, 0), TraktMediaType.MOVIE, 3, LocalDateTime.of(2014, Month.APRIL, 1, 0, 0), true)

        // TODO: Refactor WathTimeGuessimator so that it can be tested without console in/out

    }
}