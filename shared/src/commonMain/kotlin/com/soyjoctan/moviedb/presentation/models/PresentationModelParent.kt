package com.soyjoctan.moviedb.presentation.models

abstract class PresentationModelParent {
    abstract var movieName: String?
    abstract var posterPathImage: String?
    abstract var popularity: Double?
    abstract var movieId: Long?
    abstract var backdropPath: String?
}
