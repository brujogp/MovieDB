package com.soyjoctan.moviedb.domain.use_cases

import com.soyjoctan.moviedb.shared.cache.MovieDataSkd

class DeleteItemToWatchByIdUseCase {
    operator fun invoke(sdk: MovieDataSkd, itemToRemove: Long) {
        try {
            sdk.deleteItemToWatchById(itemToRemove)
        } catch (nullPointer: NullPointerException) {
            print(nullPointer.stackTraceToString())
        } catch (e: Exception) {
            print(e.stackTraceToString())
        }
    }
}