package com.soyjoctan.moviedb.domain.use_cases

import com.soyjoctan.moviedb.data.model.entities.ItemToWatch
import com.soyjoctan.moviedb.presentation.models.GenreModel
import com.soyjoctan.moviedb.presentation.models.ItemToWatchModel
import com.soyjoctan.moviedb.shared.cache.MovieDataSkd
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class AddItemForWatchUseCase {
    operator fun invoke(sdk: MovieDataSkd, itemToWatch: ItemToWatchModel) {
        try {
            var itemToWatchDb: ItemToWatch? = null
            itemToWatch.apply {
                itemToWatchDb = ItemToWatch(
                    itemId = itemId,
                    itemName = itemName,
                    whereWatch = whereWatch,
                    posterPathImage = posterPathImage,
                    popularity = popularity,
                    backdropPath = backdropPath,
                    genres = getJsonFromList(genres),
                    dateAdded = dateAdded
                )
            }

            sdk.addItemForWatch(itemToWatchDb!!)
        } catch (e: Exception) {
            print(e.stackTraceToString())
        }
    }

    private fun getJsonFromList(genres: List<GenreModel>?): String {
        return Json.encodeToString(genres)
    }
}