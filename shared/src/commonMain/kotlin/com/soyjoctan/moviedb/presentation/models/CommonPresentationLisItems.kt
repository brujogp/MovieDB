package com.soyjoctan.moviedb.presentation.models

data class CommonPresentationLisItems(
    override var itemName: String?,
    override var posterPathImage: String?,
    override var popularity: Double?,
    override var itemId: Long?,
    override var backdropPath: String?,
    override var genres: List<GenreModel>?,
    override var dateAdded: String?
): AbstractCommonPresentationListItems()
