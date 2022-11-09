package com.soyjoctan.moviedb.data.repository.requests

import io.ktor.resources.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
@Resource("/3/movie")
class MovieDetailsRequest {
    @Serializable
    @Resource("/{movie_id}")
    class Id(
        val parent: MovieDetailsRequest = MovieDetailsRequest(),
        @SerialName("movie_id") val movieId: Long
    )
}