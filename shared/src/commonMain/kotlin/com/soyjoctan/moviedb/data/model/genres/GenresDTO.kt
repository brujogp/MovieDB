package com.soyjoctan.moviedb.data.model.genres

import kotlinx.serialization.*

@Serializable
data class GenresDTO(
    val genres: List<Genre>? = null
)

@Serializable
data class Genre(
    val id: Long? = null,
    val name: String? = null
)
