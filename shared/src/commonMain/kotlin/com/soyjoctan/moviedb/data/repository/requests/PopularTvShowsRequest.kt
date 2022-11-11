package com.soyjoctan.moviedb.data.repository.requests

import io.ktor.resources.*
import kotlinx.serialization.Serializable


@Serializable
@Resource("/3//tv/popular")
class PopularTvShowsRequest
