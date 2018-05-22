package net.emteeware

import java.time.DateTimeException
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class WatchTimeGuessimator(var mediaList: List<Media>) {


    fun askUserByDate(startDate: LocalDate, endDate: LocalDate): List<Media>{
        for(media in mediaList) {
            val formatter = DateTimeFormatter.ofPattern("E, dd.MM.YYYY")
            if(!media.watchTimeSet && media.viewDate.isBefore(endDate.atStartOfDay().plusDays(1)) && media.viewDate.isAfter(startDate.atStartOfDay())) {
                askUserForWatchTime(media, formatter)
            }
        }
        return mediaList
    }

    fun askUserByType(mediaType: TraktMediaType): List<Media>{
        for(media in mediaList) {
            val formatter = DateTimeFormatter.ofPattern("E, dd.MM.YYYY")
            if(!media.watchTimeSet && media.type == mediaType) {
                askUserForWatchTime(media, formatter)
            }
        }
        return mediaList
    }

    private fun askUserForWatchTime(media: Media, formatter: DateTimeFormatter?) {
        println("At what time did you finish watching ${media.name} on ${media.viewDate.format(formatter)}?")
        var finishingHour: Int? = null
        while (finishingHour == null) {
            try {
                finishingHour = readLine()!!.toInt()
                media.viewDate = LocalDateTime.of(media.viewDate.toLocalDate(), LocalTime.of(finishingHour, 0))
                media.watchTimeSet = true
            } catch (e: Exception) {
                when (e) {
                    is NumberFormatException, is DateTimeException -> {
                        finishingHour = null
                    }
                    else -> throw e
                }
            }
        }
    }
}