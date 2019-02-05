package net.emteeware

import java.net.URL
import java.time.LocalDate

data class Media (val position: Int,
                  val imdbId: String,
                  val watchedAt: LocalDate,
                  val title: String,
                  val imdbUrl: URL,
                  val mediaType: TraktMediaType,
                  val yourRating: Int)