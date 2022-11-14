package com.soyjoctan.moviedb.domain.use_cases

import com.soyjoctan.moviedb.data.model.dtos.WrapperStatusInfo
import com.soyjoctan.moviedb.data.model.entities.ItemToWatch
import com.soyjoctan.moviedb.shared.cache.MovieDataSkd
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AddItemForWatchUseCase {
    operator fun invoke(sdk: MovieDataSkd, itemToWatch: ItemToWatch) {
        try {
            sdk.addItemForWatch(itemToWatch)
        } catch (e: Exception) {
            print(e.stackTraceToString())
        }
    }
}