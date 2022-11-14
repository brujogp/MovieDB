package com.soyjoctan.moviedb.shared.cache

import com.soyjoctan.moviedb.data.model.entities.ItemToWatch

class MovieDataSkd(databaseDriverFactory: DatabaseDriverFactory) {
    private val database = Database(databaseDriverFactory)

    @Throws(Exception::class)
    fun getMoviesToWatch(forceReload: Boolean = false): List<ItemsToWatch> {
        return database.getAllItemsToWatch()
    }

    @Throws(Exception::class)
    fun addItemForWatch(itemToAdd: ItemToWatch) {
        try {
            database.addItemToWatch(itemToAdd)
        } catch (e: Exception) {
            print(e.stackTraceToString())
        }
    }
}