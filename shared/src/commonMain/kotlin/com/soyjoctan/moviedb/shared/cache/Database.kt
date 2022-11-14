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

    internal fun getAllItemsLiked(): List<com.soyjoctan.moviedb.shared.cache.ItemsLiked> {
        return dbQuery.selectItemsLiked().executeAsList()
    }

    internal fun getAllItemsToWatch(): List<com.soyjoctan.moviedb.shared.cache.ItemsToWatch> {
        return dbQuery.selectItemsToWatch().executeAsList()
    }

    internal fun addItemToWatch(moviesToWatch: ItemToWatch) {
        dbQuery.transaction {
            insertMovieToWatch(moviesToWatch)
        }
    }


    private fun insertMovieToWatch(itemToWatch: ItemToWatch) {
        dbQuery.insertItemsToWatch(
            itemId = itemToWatch.itemId!!,
            itemName = itemToWatch.itemName!!,
            whereWatch = itemToWatch.whereWatch,
            posterPathImage = itemToWatch.posterPathImage,
            popularity = itemToWatch.popularity?.toLong(),
            backdropPath = itemToWatch.backdropPath
        )
    }
}