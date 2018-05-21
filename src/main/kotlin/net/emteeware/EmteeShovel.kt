package net.emteeware

import java.net.URI

fun main(args: Array<String>) {
    println("Shoveling data …")
    val mediaImporter = MediaImporter()
    val importMediaList = mediaImporter.importMediaList(URI(args[0]))
    importMediaList.sorted().forEach {m ->println(m)}
}

