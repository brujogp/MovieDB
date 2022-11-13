package com.soyjoctan.moviedb.domain.use_cases

import com.soyjoctan.moviedb.data.model.dtos.WrapperStatusInfo
import com.soyjoctan.moviedb.shared.cache.MovieDataSkd
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ItemsForWatchUseCase {

    operator fun invoke(sdk: MovieDataSkd): Flow<WrapperStatusInfo> = flow {
        val result = sdk.getMoviesToWatch()

        if (result.isEmpty()) {
            emit(WrapperStatusInfo.notFound)
        } else {
            emit(WrapperStatusInfo.SuccessResponse(result))
        }
    }
}