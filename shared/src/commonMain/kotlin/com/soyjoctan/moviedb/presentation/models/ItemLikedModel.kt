package com.soyjoctan.moviedb.presentation.models

data class ItemLikedModel(
    override var itemId: Long?,
    override var itemName: String?,
    override var posterPathImage: String?,
    override var popularity: Double?,
    override var backdropPath: String?,
    override var genres: List<GenreModel>?,
    val fromListToWatch: Boolean,
    override var dateAdded: String?
) : AbstractCommonPresentationListItems()
