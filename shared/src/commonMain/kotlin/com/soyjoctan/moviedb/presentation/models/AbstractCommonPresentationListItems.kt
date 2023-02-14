package com.soyjoctan.moviedb.presentation.models

abstract class AbstractCommonPresentationListItems : PresentationModelParent() {
    abstract override var itemName: String?
    abstract override var posterPathImage: String?
    abstract override var popularity: Double?
    abstract override var itemId: Long?
    abstract override var backdropPath: String?
    abstract var genres: List<GenreModel>?
    abstract var dateAdded: String?
}
