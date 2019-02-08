package net.emteeware

import tornadofx.*

class MediaModel(var medium: Media) : ViewModel() {
    val position = bind { medium.positionProperty }
    val watchDate = bind { medium.watchDateProperty }
    val title = bind { medium.titleProperty }
    val imdbId = bind { medium.imdbIdProperty }
    val imdbUrl = bind { medium.imdbUrlProperty }
    val mediaType = bind { medium.mediaTypeProperty }
    val yourRating = bind { medium.yourRatingProperty }
    val addToImport = bind { medium.addToImportProperty }
    val watchTime = bind { medium.watchTimeProperty }
    val watchTimeSet = bind { medium.watchTimeSetProperty }
    val watchHour = bind { medium.watchHourProperty }
    val watchMinute = bind { medium.watchMinuteProperty }
}