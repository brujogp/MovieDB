package com.soyjoctan.moviedb.presentation.models

import kotlinx.serialization.Serializable

@Serializable
data class GenreModel(
    val id: Long? = null,
    val name: String? = null
)
