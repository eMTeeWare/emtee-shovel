package net.emteeware

import com.natpryce.konfig.*
import com.natpryce.konfig.ConfigurationProperties.Companion.systemProperties
import java.time.LocalDate


fun main(args: Array<String>) {

    val imdbViewingHistory = ImdbViewingHistory()
    imdbViewingHistory.importFromCsv(args[0])
    imdbViewingHistory.removeUnrated()
    imdbViewingHistory.removeSeenAfter(LocalDate.of(2018, 4, 21))
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

