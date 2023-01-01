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
    operator fun invoke(
        sdk: MovieDataSkd,
        filterByGenres: ArrayList<GenreModel>? = null
    ): Flow<WrapperStatusInfo> = flow {
        var resultToPresentation: ArrayList<ItemToWatchModel> = arrayListOf()

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


            filterByGenres?.let {
                if (filterByGenres.size > 0)
                    resultToPresentation = filterByGenres(resultToPresentation, it)
            }
        } catch (e: Exception) {
            print(e.stackTraceToString())
        }

        println("Hola")

        if (resultToPresentation.isEmpty()) {
            emit(WrapperStatusInfo.NotFound)
        } else {
            emit(WrapperStatusInfo.SuccessResponse(resultToPresentation))
        }
    }

    private fun filterByGenres(
        resultToPresentation: ArrayList<ItemToWatchModel>,
        listGenres: ArrayList<GenreModel>
    ): ArrayList<ItemToWatchModel> {
        val s: ArrayList<ItemToWatchModel> = arrayListOf()

        listGenres.forEach { genre: GenreModel ->
            val temporalResult = resultToPresentation.filter { it.genres!!.contains(genre) }
            temporalResult.forEach {
                s.add(it)
            }
        }

        return s
    }
}