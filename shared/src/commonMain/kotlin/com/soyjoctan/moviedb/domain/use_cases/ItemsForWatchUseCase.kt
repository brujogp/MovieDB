package com.soyjoctan.moviedb.domain.use_cases

import com.soyjoctan.moviedb.data.model.dtos.WrapperStatusInfo
import com.soyjoctan.moviedb.shared.cache.ItemsToWatch
import com.soyjoctan.moviedb.shared.cache.MovieDataSkd
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ItemsForWatchUseCase {

    operator fun invoke(sdk: MovieDataSkd): Flow<WrapperStatusInfo> = flow {
        var result: List<ItemsToWatch> = listOf()

        try {
            result = sdk.getMoviesToWatch()
        } catch (e: Exception) {
            print(e.stackTraceToString())
        }

        if (result.isEmpty()) {
            emit(WrapperStatusInfo.NotFound)
        } else {
            emit(WrapperStatusInfo.SuccessResponse(result))
        }
    }
}