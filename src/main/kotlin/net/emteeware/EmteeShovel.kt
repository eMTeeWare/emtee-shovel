package net.emteeware

import java.net.URI
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Month

fun main(args: Array<String>) {
    val mediaImporter = MediaImporter()
    mediaImporter.executeImport(args)
}

