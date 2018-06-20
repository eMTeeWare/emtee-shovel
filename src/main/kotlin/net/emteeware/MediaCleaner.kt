package net.emteeware

class MediaCleaner {
    fun deduplicateMedia(importMediaList: MediaList, rewatchThreshold: Int): MediaList {
        importMediaList.deduplicate(rewatchThreshold)
        return importMediaList
    }

    fun deduplicateMedia(importMediaList: List<Media>, rewatchThreshold: Int): MediaList {
        val mediaList = MediaList()
        for(media in importMediaList) mediaList.add(media)
        mediaList.deduplicate(rewatchThreshold)
        return mediaList
    }

    fun removeUnsupportedMedia(importMediaList: MediaList): MediaList {
        importMediaList.removeUnsupportedMedia()
        return importMediaList
    }

    fun removeUnsupportedMedia(importMediaList: List<Media>): MediaList {
        val mediaList = MediaList()
        importMediaList.forEach { m -> mediaList.add(m) }
        mediaList.removeUnsupportedMedia()
        return mediaList
    }
}
