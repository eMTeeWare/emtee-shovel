package net.emteeware

import java.net.URI

fun main(args: Array<String>) {
    println("Shoveling data …")
    val mediaImporter = MediaImporter()
    var importMediaList = mediaImporter.importMediaList(URI(args[0]))
    val importedMediaCount = importMediaList.size
    println("$importedMediaCount media imported")
    val mediaCleaner = MediaCleaner()
    importMediaList = mediaCleaner.deduplicateMedia(importMediaList, 14)
    val removedDuplicateMediaCount = importedMediaCount - importMediaList.size
    println("$removedDuplicateMediaCount media removed")
}

