package net.emteeware

import java.time.LocalTime

class TvShowLibrary {
    val showList = HashMap<String, TvShow>()

    fun addShow(showName: String) {
        showList.put(showName, TvShow(showName))
    }

    fun doesContain(showName: String) : Boolean {
        return showList.containsKey(showName)
    }

    fun getWatchTime(showName: String) : LocalTime {
        return showList[showName]?.defaultWatchTime ?: LocalTime.MIDNIGHT
    }

    fun updateWatchTime(show: TvShow) {
        showList[show.name]?.defaultWatchTime = show.defaultWatchTime
    }
}