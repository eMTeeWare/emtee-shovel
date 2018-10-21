package net.emteeware

import mu.KLogging
import java.io.File
import java.time.LocalDateTime
import java.time.Month

class MediaImporter {
    var importMediaList = MediaList()

    companion object: KLogging()

    fun executeImport(args: Array<String>) {
        logger.info {"Shoveling data …"}
        val csvImporter = CsvImporter()
        val endDateOfMediaToBeImported = LocalDateTime.of(2018, Month.APRIL, 21, 22, 4)
        val fileToBeImported = File(args[0]).toURI()

        importMediaList = csvImporter.importMediaList(fileToBeImported, endDateOfMediaToBeImported)
        logger.info {"${importMediaList.size} media imported"}

        val removedInvalidTypeCount = importMediaList.removeUnsupportedMedia()
        logger.info {"$removedInvalidTypeCount invalid media removed"}

        val removedDuplicateMediaCount = importMediaList.deduplicate(14)
        logger.info {"$removedDuplicateMediaCount duplicate media removed"}

        val tvShowLibrary = TvShowLibrary(importMediaList)

        tvShowLibrary.printShows()
        tvShowLibrary.uncertainShowTitles.sorted().forEach(::println)
    }
}