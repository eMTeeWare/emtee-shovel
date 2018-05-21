package net.emteeware

import java.lang.Math.abs
import java.time.temporal.ChronoUnit

class MediaCleaner {
    fun deduplicateMedia(importMediaList: List<Media>, rewatchThreshold : Int): List<Media> {
        val sortedMediaList = importMediaList.sorted()
        val mediaToBeRemoved = ArrayList<Media>()
        val upperBound = sortedMediaList.size - 2
        for (i in 0..upperBound ) {
            if(sortedMediaList[i].imdbId == sortedMediaList[i+1].imdbId) {
                val daysBetweenViewing = ChronoUnit.DAYS.between(sortedMediaList[i + 1].viewDate, sortedMediaList[i].viewDate)
                if(abs(daysBetweenViewing) < rewatchThreshold) {
                    mediaToBeRemoved += sortedMediaList[i]
                }
            }
        }
        return sortedMediaList.minus(mediaToBeRemoved)
    }
}
