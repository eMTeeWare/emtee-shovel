package net.emteeware

import java.net.URI
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Month

fun main(args: Array<String>) {
    println("Shoveling data …")
    val mediaImporter = MediaImporter()
    var importMediaList = mediaImporter.importMediaList(URI(args[0]), LocalDateTime.of(2018, Month.APRIL, 21, 22, 4))
    var remainingMediaCount = importMediaList.size
    println("$remainingMediaCount media imported")
    val mediaCleaner = MediaCleaner()
    importMediaList = mediaCleaner.removeUnsupportedMedia(importMediaList)
    val removedInvalidTypeCount = remainingMediaCount - importMediaList.size
    println("$removedInvalidTypeCount invalid media removed")
    remainingMediaCount -= removedInvalidTypeCount
    importMediaList = mediaCleaner.deduplicateMedia(importMediaList, 14)
    val removedDuplicateMediaCount = remainingMediaCount - importMediaList.size
    println("$removedDuplicateMediaCount duplicate media removed")
    remainingMediaCount -= removedDuplicateMediaCount

    val watchTimeGuessimator = WatchTimeGuessimator(importMediaList)
    val startDateForManualWatchTimeQuestion = LocalDate.of(2018, Month.APRIL, 19)
    val endDateForManualWatchTimeQuestion = LocalDate.now()
    importMediaList = watchTimeGuessimator.askUserByDate(startDateForManualWatchTimeQuestion, endDateForManualWatchTimeQuestion)
    importMediaList.filter { m -> m.watchTimeSet }.forEach(::println)

//    importMediaList = watchTimeGuessimator.askUserByType(TraktMediaType.MOVIE)
//    importMediaList.filter { m -> m.watchTimeSet }.forEach(::println)


}

