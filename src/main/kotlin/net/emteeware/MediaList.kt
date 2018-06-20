package net.emteeware

import java.time.temporal.ChronoUnit

class MediaList  {
    constructor()

    constructor(nativeMediaList : List<Media>) {
        nativeMediaList.forEach( {m -> this.mediaList.add(m)} )
    }

    fun add(media: Media) {
        mediaList.add(media)
    }

    private val mediaList = ArrayList<Media>()
    val size get() = mediaList.size

    fun sorted() : List<Media> {
        return mediaList.sorted()
    }

    fun removeUnsupportedMedia() {
        val removeMediaList = ArrayList<Media>()
        for(media in mediaList) {
            if(media.type == TraktMediaType.UNDEFINED) removeMediaList.add(media)
        }
        mediaList.removeAll(removeMediaList)
    }

    fun deduplicate(rewatchThreshold: Int) {
        val sortedMediaList = mediaList.sorted()
        val mediaToBeRemoved = ArrayList<Media>()
        val upperBound = sortedMediaList.size - 2
        for (i in 0..upperBound) {
            if (sortedMediaList[i].imdbId == sortedMediaList[i + 1].imdbId) {
                val daysBetweenViewing = ChronoUnit.DAYS.between(sortedMediaList[i + 1].viewDate, sortedMediaList[i].viewDate)
                if (Math.abs(daysBetweenViewing) < rewatchThreshold) {
                    mediaToBeRemoved += sortedMediaList[i]
                }
            }
        }
        mediaList.removeAll(mediaToBeRemoved)
    }
}
