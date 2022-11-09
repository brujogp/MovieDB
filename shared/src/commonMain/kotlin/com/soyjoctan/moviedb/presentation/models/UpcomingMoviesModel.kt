package com.soyjoctan.moviedb.presentation.models

data class UpcomingMoviesModel(
    override var movieName: String?,
    override var posterPathImage: String?,
    override var popularity: Double?,
    override var movieId: Long?
) : PresentationModelParent()