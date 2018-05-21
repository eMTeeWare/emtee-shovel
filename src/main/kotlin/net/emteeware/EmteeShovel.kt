package net.emteeware

import java.net.URI

fun main(args: Array<String>) {
    println("Shoveling data …")
    val mediaImporter = MediaImporter()
    var importMediaList = mediaImporter.importMediaList(URI(args[0]))
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
}

