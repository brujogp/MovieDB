package com.soyjoctan.moviedb.domain.use_cases

import com.soyjoctan.moviedb.data.model.dtos.WrapperStatusInfo

import com.soyjoctan.moviedb.presentation.models.GenreModel
import com.soyjoctan.moviedb.presentation.models.ItemToWatchModel
import com.soyjoctan.moviedb.shared.cache.ItemsToWatch
import com.soyjoctan.moviedb.shared.cache.MovieDataSkd
import io.ktor.http.HttpHeaders.Date
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.datetime.LocalDate
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class ItemsForWatchUseCase {
    operator fun invoke(sdk: MovieDataSkd): Flow<WrapperStatusInfo> = flow {
        val resultToPresentation: ArrayList<ItemToWatchModel> = arrayListOf()

        try {
            val result: List<ItemsToWatch> = sdk.getMoviesToWatch()
            result.let {
                it.forEach { itemToWatch ->
                    resultToPresentation.add(
                        ItemToWatchModel(
                            itemToWatch.itemId,
                            itemToWatch.itemName,
                            itemToWatch.whereWatch ?: "",
                            itemToWatch.posterPathImage,
                            itemToWatch.popularity?.toDouble(),
                            itemToWatch.backdropPath,
                            Json.decodeFromString<List<GenreModel>>(itemToWatch.genres!!),
                            itemToWatch.dateAdded
                        )
                    )
                }
            }

            resultToPresentation.sortWith(compareByDescending {
                it.dateAdded
            })

        } catch (e: Exception) {
            print(e.stackTraceToString())
        }

        if (resultToPresentation.isEmpty()) {
            emit(WrapperStatusInfo.NotFound)
        } else {
            emit(WrapperStatusInfo.SuccessResponse(resultToPresentation))
        }
    }
}