package com.soyjoctan.moviedb.data.repository.requests

import io.ktor.resources.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Resource("/3/discover/movie")
class DiscoverMovies(val page: Long? = 1, @SerialName("with_genres") val withGenres: Long?, @SerialName("include_adult") val includeAdult: Boolean? = false)