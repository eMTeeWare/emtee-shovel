package net.emteeware

import java.time.LocalTime

data class TvShow(
        val name : String,
        var defaultWatchTime: LocalTime = LocalTime.MIDNIGHT
)