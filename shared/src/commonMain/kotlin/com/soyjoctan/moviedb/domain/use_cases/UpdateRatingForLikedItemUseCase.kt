package com.soyjoctan.moviedb.domain.use_cases

import com.soyjoctan.moviedb.shared.cache.MovieDataSkd

class UpdateRatingForLikedItemUseCase {
    operator fun invoke(sdk: MovieDataSkd, newRating: Double, itemId: Long) {
        try {
            sdk.updateRatingForLikedItem(newRating, itemId)
        } catch (e: Exception) {
            print(e.stackTraceToString())
        }
    }
}