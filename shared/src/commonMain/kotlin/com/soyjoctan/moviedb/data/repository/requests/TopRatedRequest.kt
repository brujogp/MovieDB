package com.soyjoctan.moviedb.data.repository.requests

import io.ktor.resources.*
import kotlinx.serialization.*

@Serializable
@Resource("/3/movie/top_rated")
class TopRatedRequest