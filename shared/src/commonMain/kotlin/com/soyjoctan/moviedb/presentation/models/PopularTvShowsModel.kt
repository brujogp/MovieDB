package com.soyjoctan.moviedb.presentation.models

data class PopularTvShowsModel(
    override var itemName: String?,
    override var posterPathImage: String?,
    override var popularity: Double?,
    override var itemId: Long?,
    override var backdropPath: String?
) : PresentationModelParent()
