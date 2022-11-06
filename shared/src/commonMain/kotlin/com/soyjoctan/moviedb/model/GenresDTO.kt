package com.soyjoctan.moviedb.model

import kotlinx.serialization.*

@Serializable
data class GenresDTO(
    val genres: List<Genre>? = null
)
