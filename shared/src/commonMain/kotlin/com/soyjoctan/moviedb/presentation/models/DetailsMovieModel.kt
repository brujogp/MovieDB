package com.soyjoctan.moviedb.presentation.models

import com.soyjoctan.moviedb.data.model.moviedetails.Genre

data class DetailsMovieModel(
    override var movieName: String?,
    override var posterPathImage: String?,
    override var popularity: Double?,
    override var movieId: Long?,
    override var backdropPath: String?,
    val budget: Long?,
    val genres: List<Genre>? = null,
    val homepage: String?,
    val originalLanguage: String? = null,
    val originalTitle: String? = null,
    val overview: String? = null,
    val releaseDate: String? = null,
    val voteAverage: Double? = null,
    val productionCompanies: List<ProductionCompany>? = null,
    val productionCountries: List<ProductionCountry>? = null,
    val spokenLanguages: List<SpokenLanguage>? = null,
) : PresentationModelParent()

data class ProductionCompany(
    val logoPath: String? = null,
    val name: String? = null,
)

data class ProductionCountry(
    val name: String? = null
)

data class SpokenLanguage(
    val name: String? = null
)
