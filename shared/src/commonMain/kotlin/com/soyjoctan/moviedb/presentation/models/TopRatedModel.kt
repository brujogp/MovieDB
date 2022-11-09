package com.soyjoctan.moviedb.presentation.models

data class TopRatedModel(
    override var movieName: String?,
    override var posterPathImage: String?,
    override var popularity: Double?,
    override var movieId: Long?,
    override var backdropPath: String?
) : PresentationModelParent()
