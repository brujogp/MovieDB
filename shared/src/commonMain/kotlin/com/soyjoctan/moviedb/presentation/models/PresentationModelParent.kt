package com.soyjoctan.moviedb.presentation.models

abstract class PresentationModelParent {
    abstract var itemName: String?
    abstract var posterPathImage: String?
    abstract var popularity: Double?
    abstract var itemId: Long?
    abstract var backdropPath: String?
}
