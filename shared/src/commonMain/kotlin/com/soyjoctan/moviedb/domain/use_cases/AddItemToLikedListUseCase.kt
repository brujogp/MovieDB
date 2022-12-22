package com.soyjoctan.moviedb.domain.use_cases

import com.soyjoctan.moviedb.data.model.entities.ItemLiked
import com.soyjoctan.moviedb.data.model.entities.ItemToWatch
import com.soyjoctan.moviedb.presentation.models.GenreModel
import com.soyjoctan.moviedb.presentation.models.ItemLikedModel
import com.soyjoctan.moviedb.shared.cache.MovieDataSkd
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class AddItemToLikedListUseCase {
    operator fun invoke(sdk: MovieDataSkd, itemLiked: ItemLikedModel) {
        try {
            var itemLikedDb: ItemLiked? = null
            itemLiked.apply {
                itemLikedDb = ItemLiked(
                    itemId = itemId,
                    itemName = itemName,
                    posterPathImage = posterPathImage,
                    popularity = popularity,
                    backdropPath = backdropPath,
                    genres = getJsonFromList(genres),
                    dateAdded = dateAdded!!,
                    fromListToWatch = fromListToWatch
                )
            }

            sdk.addItemToLikedList(itemLikedDb!!)
        } catch (e: Exception) {
            print(e.stackTraceToString())
        }
    }

    private fun getJsonFromList(genres: List<GenreModel>?): String {
        return Json.encodeToString(genres)
    }
}