package com.soyjoctan.moviedb.domain.use_cases

import com.soyjoctan.moviedb.data.model.dtos.WrapperStatusInfo
import com.soyjoctan.moviedb.shared.cache.ItemsToWatch
import com.soyjoctan.moviedb.shared.cache.MovieDataSkd
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

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