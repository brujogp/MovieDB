package com.soyjoctan.moviedb.domain.use_cases

import com.soyjoctan.moviedb.data.model.dtos.WrapperStatusInfo
import com.soyjoctan.moviedb.shared.cache.ItemsLiked
import com.soyjoctan.moviedb.shared.cache.ItemsToWatch
import com.soyjoctan.moviedb.shared.cache.MovieDataSkd
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SearchLikedItemByIdUseCase {
    operator fun invoke(sdk: MovieDataSkd, itemToSearch: Long): Flow<WrapperStatusInfo> = flow {
        try {
            val result: ItemsLiked? = sdk.getLikedItemById(itemToSearch)
            if (result == null) {
                emit(WrapperStatusInfo.NotFound)
            } else {
                emit(WrapperStatusInfo.SuccessResponse(result))
            }
        } catch (nullPointer: NullPointerException) {
            emit(WrapperStatusInfo.NotFound)
        } catch (e: Exception) {
            emit(WrapperStatusInfo.NotFound)
        }
    }
}