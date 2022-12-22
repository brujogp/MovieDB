package com.soyjoctan.moviedb.shared.cache

import com.soyjoctan.moviedb.data.model.entities.ItemLiked
import com.soyjoctan.moviedb.data.model.entities.ItemToWatch

internal class Database(databaseDriverFactory: DatabaseDriverFactory) {
    private val database = AppDatabase(databaseDriverFactory.createDriver())
    private val dbQuery = database.appDatabaseQueries

    internal fun getAllItemsLiked(): List<ItemsLiked> {
        return dbQuery.selectItemsLiked().executeAsList()
    }

    internal fun getAllItemsToWatch(): List<ItemsToWatch> {
        return dbQuery.selectItemsToWatch().executeAsList()
    }

    internal fun searchItemToWatchById(itemId: Long): ItemsToWatch {
        return dbQuery.searchItemToWatchById(itemId).executeAsOne()
    }

    internal fun searchLikedItemById(itemId: Long): ItemsLiked {
        return dbQuery.searchLikedItemById(itemId).executeAsOne()
    }

    internal fun deleteItemToWatchById(itemId: Long) {
        return dbQuery.removeItemToWatchById(itemId)
    }

    internal fun addItemToWatch(moviesToWatch: ItemToWatch) {
        dbQuery.transaction {
            insertMovieToWatch(moviesToWatch)
        }
    }

    internal fun addItemToLikedList(moviesLiked: ItemLiked) {
        dbQuery.transaction {
            insertMovieToLikedList(moviesLiked)
        }
    }

    private fun insertMovieToWatch(itemToWatch: ItemToWatch) {
        itemToWatch.apply {
            dbQuery.insertItemsToWatch(
                itemId = itemId!!,
                itemName = itemName!!,
                whereWatch = whereWatch,
                posterPathImage = posterPathImage,
                popularity = popularity?.toLong(),
                backdropPath = backdropPath,
                genres = genres,
                dateAdded = dateAdded
            )
        }
    }

    private fun insertMovieToLikedList(itemLiked: ItemLiked) {
        itemLiked.apply {
            dbQuery.insertLikedMovie(
                itemId = itemId!!,
                itemName = itemName!!,
                posterPathImage = posterPathImage!!,
                backdropPath = backdropPath,
                genres = genres,
                dateAdded = dateAdded,
                fromListToWatch = if (itemLiked.fromListToWatch) 1 else 0
            )
        }
    }
}
