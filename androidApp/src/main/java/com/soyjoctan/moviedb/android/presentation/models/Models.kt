package com.soyjoctan.moviedb.android.presentation.models

import com.soyjoctan.moviedb.presentation.models.PresentationModelParent

data class CarouselModel(
    override var movieName: String?,
    override var posterPathImage: String?,
    override var popularity: Double?,
    override var movieId: Long?,
    override var backdropPath: String?
) : PresentationModelParent()