package com.soyjoctan.moviedb.presentation.models

data class UpcomingMoviesModel(
    override var itemName: String?,
    override var posterPathImage: String?,
    override var popularity: Double?,
    override var itemId: Long?,
    override var backdropPath: String?
) : PresentationModelParent()