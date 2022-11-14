package com.soyjoctan.moviedb.data.repository.requests

import io.ktor.resources.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Resource("/3/search/multi")
class SearchItemsRequest(val page: Long? = 1, val query: String)