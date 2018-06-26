package net.emteeware

import com.natpryce.konfig.*
import com.natpryce.konfig.ConfigurationProperties.Companion.systemProperties


fun main(args: Array<String>) {
    val user_name = Key("user.name", stringType)
    val config =
            systemProperties() overriding
            EnvironmentVariables() overriding
            // ConfigurationProperties.fromFile() overriding
            ConfigurationProperties.fromResource("defaults.properties")
    println("Hallo ${config[user_name]}")

    val mediaImporter = MediaImporter()
    mediaImporter.executeImport(args)
}

