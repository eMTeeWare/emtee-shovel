package net.emteeware

import java.time.LocalTime

class TvShowLibrary(importMediaList: MediaList) {
    internal val showList = HashMap<String, TvShow>()
    internal val uncertainShowTitles = ArrayList<String>()

    init {
        for(media in importMediaList.sorted()) when {
            media.type == TraktMediaType.EPISODE -> {
                if(media.name.filter { c -> c == ':' }.count() > 1) {
                    uncertainShowTitles.add(media.name)
                } else {
                    val showName = media.name.substringBeforeLast(':', "UNKNOWN SHOW")
                    showList[showName] = TvShow(showName)
                }
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