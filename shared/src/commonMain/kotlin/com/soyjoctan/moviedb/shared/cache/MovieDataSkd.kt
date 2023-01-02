package com.soyjoctan.moviedb.shared.cache

import com.soyjoctan.moviedb.data.model.entities.ItemLiked
import com.soyjoctan.moviedb.data.model.entities.ItemToWatch

class MovieDataSkd(databaseDriverFactory: DatabaseDriverFactory) {
    private val database = Database(databaseDriverFactory)

    @Throws(Exception::class)
    fun getMoviesToWatch(forceReload: Boolean = false): List<ItemsToWatch> {
        return database.getAllItemsToWatch()
    }

    @Throws(Exception::class)
    fun getLikedItems(): List<ItemsLiked> {
        return database.getAllItemsLiked()
    }

    @Throws(Exception::class)
    fun getItemToWatchById(idItem: Long): ItemsToWatch {
        return database.searchItemToWatchById(idItem)
    }

    @Throws(Exception::class)
    fun getLikedItemById(idItem: Long): ItemsLiked {
        return database.searchLikedItemById(idItem)
    }

    @Throws(Exception::class)
    fun deleteItemToWatchById(idItem: Long) {
        return database.deleteItemToWatchById(idItem)
    }

    @Throws(Exception::class)
    fun deleteLikedItemById(idItem: Long) {
        return database.deleteLikedItemById(idItem)
    }

    @Throws(Exception::class)
    fun addItemForWatch(itemToAdd: ItemToWatch) {
        try {
            database.addItemToWatch(itemToAdd)
        } catch (e: Exception) {
            print(e.stackTraceToString())
        }
    }

    @Throws(Exception::class)
    fun addItemToLikedList(moviesLiked: ItemLiked) {
        try {
            database.addItemToLikedList(moviesLiked)
        } catch (e: Exception) {
            print(e.stackTraceToString())
        }
    }

    @Throws(Exception::class)
    fun updateRatingForLikedItem(newRating: Double, itemId: Long) {
        try {
            database.updateRatingForLikedItem(newRating, itemId)
        } catch (e: Exception) {
            print(e.stackTraceToString())
        }
    }
}