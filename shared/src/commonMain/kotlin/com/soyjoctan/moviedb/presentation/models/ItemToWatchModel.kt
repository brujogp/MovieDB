package com.soyjoctan.moviedb.presentation.models

data class ItemToWatchModel(
    override var itemId: Long?,
    override var itemName: String?,
    val whereWatch: String,
    override var posterPathImage: String?,
    override var popularity: Double?,
    override var backdropPath: String?,
    val genres: List<GenreModel>?,
    val dateAdded: String?
) : PresentationModelParent()
