package com.soyjoctan.moviedb.data.repository.requests

import io.ktor.resources.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
@Resource("/3/movie")
class FindByGenres {
    @Serializable
    @Resource("/{movie_id}/similar")
    class Id(val parent: FindByGenres = FindByGenres(), @SerialName("movie_id") val movieId: Long)
}