package com.soyjoctan.moviedb.presentation.models

data class ItemToWatchModel(
    override var itemId: Long?,
    override var itemName: String?,
    override var posterPathImage: String?,
    override var popularity: Double?,
    override var backdropPath: String?,
    override var genres: List<GenreModel>?,
    val whereWatch: String,
    override var dateAdded: String?
) : AbstractCommonPresentationListItems()
