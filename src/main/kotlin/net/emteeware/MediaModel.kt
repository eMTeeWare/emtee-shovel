package net.emteeware

import tornadofx.*

class MediaModel(var medium: Media) : ViewModel() {
    val position = bind { medium.positionProperty }
    val watchedAt = bind { medium.watchedAtProperty }
    val title = bind { medium.titleProperty }
    val imdbId = bind { medium.imdbIdProperty }
    val imdbUrl = bind { medium.imdbUrlProperty }
    val mediaType = bind { medium.mediaTypeProperty }
    val yourRating = bind { medium.yourRatingProperty }
    val addToImport = bind { medium.addToImportProperty }
    val watchedOn = bind { medium.watchedOnProperty }
    val watchTimeSet = bind { medium.watchTimeSetProperty }
}