package com.soyjoctan.moviedb.shared.cache

class MovieDataSkd(databaseDriverFactory: DatabaseDriverFactory) {
    private val database = Database(databaseDriverFactory)

    @Throws(Exception::class)
    suspend fun getMoviesToWatch(forceReload: Boolean = false): List<ItemsToWatch> {
         return database.getAllItemsToWatch()
    }
}