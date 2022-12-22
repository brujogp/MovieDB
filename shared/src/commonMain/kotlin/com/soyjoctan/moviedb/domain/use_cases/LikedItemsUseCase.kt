package com.soyjoctan.moviedb.domain.use_cases

import com.soyjoctan.moviedb.data.model.dtos.WrapperStatusInfo
import com.soyjoctan.moviedb.presentation.models.GenreModel
import com.soyjoctan.moviedb.presentation.models.ItemLikedModel
import com.soyjoctan.moviedb.shared.cache.ItemsLiked
import com.soyjoctan.moviedb.shared.cache.MovieDataSkd
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class LikedItemsUseCase {
    operator fun invoke(
        sdk: MovieDataSkd,
        filterByGenres: ArrayList<GenreModel>? = null
    ): Flow<WrapperStatusInfo> = flow {
        var resultToPresentation: ArrayList<ItemLikedModel> = arrayListOf()

        try {
            val result: List<ItemsLiked> = sdk.getLikedItems()
            result.let {
                it.forEach { itemLiked ->
                    resultToPresentation.add(
                        ItemLikedModel(
                            itemId = itemLiked.itemId,
                            itemName = itemLiked.itemName,
                            posterPathImage = itemLiked.posterPathImage,
                            popularity = null,
                            backdropPath = itemLiked.backdropPath,
                            genres = Json.decodeFromString<List<GenreModel>>(itemLiked.genres),
                            dateAdded = itemLiked.dateAdded,
                            fromListToWatch = itemLiked.fromListToWatch?.toInt() == 1
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

        if (resultToPresentation.isEmpty()) {
            emit(WrapperStatusInfo.NotFound)
        } else {
            emit(WrapperStatusInfo.SuccessResponse(resultToPresentation))
        }
    }


    private fun filterByGenres(
        resultToPresentation: ArrayList<ItemLikedModel>,
        listGenres: ArrayList<GenreModel>
    ): ArrayList<ItemLikedModel> {
        val s: ArrayList<ItemLikedModel> = arrayListOf()
        listGenres.forEach { genre: GenreModel ->
            val temporalResult = resultToPresentation.filter { it.genres!!.contains(genre) }
            temporalResult.forEach {
                s.add(it)
            }
        }
        return s
    }
}