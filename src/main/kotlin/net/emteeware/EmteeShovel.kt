package net.emteeware

import com.natpryce.konfig.*
import com.natpryce.konfig.ConfigurationProperties.Companion.systemProperties
import java.time.LocalDate
import java.time.Period


fun main(args: Array<String>) {

    val imdbViewingHistory = ImdbViewingHistory()
    imdbViewingHistory.importFromCsv(args[0])
    imdbViewingHistory.removeUnrated()
    imdbViewingHistory.removeSeenAfter(LocalDate.of(2018, 4, 21))
    imdbViewingHistory.removeUndefined() // Some of these should be handled as episodes or movies, there should be a manual process for that
    imdbViewingHistory.removeDuplicateCheckInsWithin(Period.ofDays(14))
    imdbViewingHistory.print()
    System.exit(0)
    val userName = Key("user.name", stringType)
    val config =
            systemProperties() overriding
            EnvironmentVariables() overriding
            // ConfigurationProperties.fromFile() overriding
            ConfigurationProperties.fromResource("defaults.properties")
    println("Hallo ${config[userName]}")

    val mediaImporter = MediaImporter()
    mediaImporter.executeImport(args)
}

