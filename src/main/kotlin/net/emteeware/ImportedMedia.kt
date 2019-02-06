package net.emteeware

import java.net.URL
import java.util.*

interface ImportedMedia {
    val Position: Int
    val Const: String
    val Created: Date
    val Title: String
    val URL: URL
    val TitleType: TraktMediaType
    val YourRating: Int
}