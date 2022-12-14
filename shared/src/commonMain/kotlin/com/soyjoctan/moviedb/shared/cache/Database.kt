package com.soyjoctan.moviedb.shared.cache

import com.soyjoctan.moviedb.data.model.entities.ItemToWatch

internal class Database(databaseDriverFactory: DatabaseDriverFactory) {
    private val database = AppDatabase(databaseDriverFactory.createDriver())
    private val dbQuery = database.appDatabaseQueries

    internal fun clearDatabase() {
        dbQuery.transaction {
            dbQuery.removeAllDataIntoItemsLiked()
            dbQuery.removeAllDataIntoItemsToWatch()
        }
    }

    internal fun getAllItemsLiked(): List<ItemsLiked> {
        return dbQuery.selectItemsLiked().executeAsList()
    }

    internal fun getAllItemsToWatch(): List<ItemsToWatch> {
        return dbQuery.selectItemsToWatch().executeAsList()
    }

    internal fun searchItemToWatchById(itemId: Long): ItemsToWatch? {
        return dbQuery.searchItemToWatchById(itemId)?.executeAsOne()
    }

    internal fun deleteItemToWatchById(itemId: Long) {
        return dbQuery.removeItemToWatchById(itemId)
    }

    internal fun addItemToWatch(moviesToWatch: ItemToWatch) {
        dbQuery.transaction {
            insertMovieToWatch(moviesToWatch)
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
}