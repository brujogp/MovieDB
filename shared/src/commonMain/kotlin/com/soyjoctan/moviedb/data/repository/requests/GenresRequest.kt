package com.soyjoctan.moviedb.data.repository.requests
import kotlinx.serialization.*
import io.ktor.resources.*

@Serializable
@Resource("/3/genre/movie/list")
class GenresRequest
