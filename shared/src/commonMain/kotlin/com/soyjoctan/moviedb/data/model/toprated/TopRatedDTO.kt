package com.soyjoctan.moviedb.data.model.toprated

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TopRatedDTO(
    val page: Long? = null,
    val results: List<Result>? = null,
    @SerialName("total_pages")
    val totalPages: Long? = null,
    @SerialName("total_results")
    val totalResults: Long? = null
)

@Serializable
data class Result (
    val adult: String? = null,
    @SerialName("backdrop_path")
    val backdropPath: String? = null,
    @SerialName("genre_ids")
    val genreIDS: List<Long>? = null,
    val id: Long? = null,
    @SerialName("original_language")
    val originalLanguage: String? = null,
    @SerialName("original_title")
    val originalTitle: String? = null,
    val overview: String? = null,
    val popularity: Double? = null,
    @SerialName("poster_path")
    val posterPath: String? = null,
    @SerialName("release_date")
    val releaseDate: String? = null,
    val title: String? = null,
    val video: Boolean? = null,
    @SerialName("vote_average")
    val voteAverage: Double? = null,
    @SerialName("vote_count")
    val voteCount: Long? = null
)