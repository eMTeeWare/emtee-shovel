package net.emteeware

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class WatchTimeGuessimator {
    fun askUserByDate(mediaList: List<Media>, startDate: LocalDate, endDate: LocalDate): List<Media>{
        for(media in mediaList) {
            val formatter = DateTimeFormatter.ofPattern("E, dd.MM.YYYY")
            if(!media.watchTimeSet && media.viewDate.isBefore(endDate.atStartOfDay().plusDays(1)) && media.viewDate.isAfter(startDate.atStartOfDay())) {
                askUserForWatchTime(media, formatter)
            }
        }
        return mediaList
    }

    fun askUserByType(mediaList: List<Media>, mediaType: TraktMediaType): List<Media>{
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
            } catch (n: NumberFormatException) {
                finishingHour = null
            }
        }
        media.viewDate = LocalDateTime.of(media.viewDate.toLocalDate(), LocalTime.of(finishingHour, 0))
        media.watchTimeSet = true
    }
}