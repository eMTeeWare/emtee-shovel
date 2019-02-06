package net.emteeware

import java.net.URL
import java.time.LocalDate
import java.time.LocalTime

data class Media (val position: Int,
                  val imdbId: String,
                  val watchedAt: LocalDate,
                  val title: String,
                  val imdbUrl: URL,
                  val mediaType: TraktMediaType,
                  val yourRating: Int,
                  val addToImport: Boolean = true,
                  val watcheOn: LocalTime = LocalTime.MIDNIGHT,
                  val watchTimeSet: Boolean = false)