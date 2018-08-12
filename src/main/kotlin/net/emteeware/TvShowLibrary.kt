package net.emteeware

import java.time.LocalTime

class TvShowLibrary(importMediaList: MediaList) {
    internal val showList = HashMap<String, TvShow>()

    init {
        for(media in importMediaList.sorted()) when {
            media.type == TraktMediaType.EPISODE -> {
                val showName = media.name.substringBeforeLast(':', "UNKNOWN SHOW")
                showList[showName] = TvShow(showName)
            }
        }
    }

    fun getWatchTime(showName: String) : LocalTime {
        return showList[showName]?.defaultWatchTime ?: LocalTime.MIDNIGHT
    }

    fun printShows() {
        showList.toSortedMap().forEach { tvShow ->
            println(tvShow)
        }
    }
}