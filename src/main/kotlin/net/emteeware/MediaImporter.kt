package net.emteeware

import java.net.URI
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Month

class MediaImporter {
    var importMediaList = MediaList()

    fun executeImport(args: Array<String>) {
        println("Shoveling data …")
        val csvImporter = CsvImporter()
        val endDateOfMediaToBeImported = LocalDateTime.of(2018, Month.APRIL, 21, 22, 4)
        val fileToBeImported = URI(args[0].replace(" ", "%20").replace("\\", "/"))
        importMediaList = csvImporter.importMediaList(fileToBeImported, endDateOfMediaToBeImported)
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
        importMediaList.sorted().filter { m -> m.watchTimeSet }.forEach(::println)

//    importMediaList = watchTimeGuessimator.askUserByType(TraktMediaType.MOVIE)
//    importMediaList.filter { m -> m.watchTimeSet }.forEach(::println)
    }
}