package net.emteeware

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue
import com.fasterxml.jackson.annotation.JsonProperty

enum class TraktMediaType {
    @JsonProperty("movie")
    MOVIE,
    @JsonProperty("tvEpisode")
    EPISODE,
    @JsonEnumDefaultValue
    UNDEFINED
}