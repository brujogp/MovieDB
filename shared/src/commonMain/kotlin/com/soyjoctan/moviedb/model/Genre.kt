package com.soyjoctan.moviedb.model

import kotlinx.serialization.*

@Serializable
data class Genre(
    val id: Long? = null,
    val name: String? = null

)
