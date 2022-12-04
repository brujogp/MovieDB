package com.soyjoctan.moviedb.data.repository.requests

import io.ktor.resources.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Resource("/3/movie")
class CreditsRequest {
    @Serializable
    @Resource("/{movie_id}/credits")
    class MovieId(
        val parent: CreditsRequest = CreditsRequest(),
        @SerialName("movie_id") val movieId: Long
    )
}