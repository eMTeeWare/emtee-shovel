package net.emteeware

class MediaList {
    fun add(media: Media) {
        mediaList.add(media)
    }

    private val mediaList = ArrayList<Media>()
    val size get() = mediaList.size
}
