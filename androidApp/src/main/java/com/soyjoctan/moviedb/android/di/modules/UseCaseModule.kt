package com.soyjoctan.moviedb.android.di.modules

import android.content.Context
import com.soyjoctan.moviedb.domain.use_cases.*
import com.soyjoctan.moviedb.shared.cache.DatabaseDriverFactory
import com.soyjoctan.moviedb.shared.cache.MovieDataSkd
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideTopRatedUseCase(): TopRatedUseCase = TopRatedUseCase()

    @Provides
    @Singleton
    fun provideUpcomingMoviesUseCase(): UpcomingMovieUseCase = UpcomingMovieUseCase()

    @Provides
    @Singleton
    fun provideGenresMoviesUseCase(): GenresUseCase = GenresUseCase()

    @Provides
    @Singleton
    fun provideFindMoviesByGenreUseCase(): FindMoviesByGenreUseCase = FindMoviesByGenreUseCase()

    @Provides
    @Singleton
    fun provideGetMovieDetailsUseCase(): DetailMoviesUseCase = DetailMoviesUseCase()

    @Provides
    @Singleton
    fun providePopularTvShowsUseCase(): PopularTvShowsUseCase = PopularTvShowsUseCase()


    @Provides
    @Singleton
    fun provideItemsToWatchUseCase(): ItemsForWatchUseCase = ItemsForWatchUseCase()

    @Provides
    @Singleton
    fun provideAddItemToWatchUseCase(): AddItemForWatchUseCase = AddItemForWatchUseCase()

    @Provides
    @Singleton
    fun provideSearchItemsUseCase(): SearchItemUseCase = SearchItemUseCase()

    @Provides
    @Singleton
    fun provideSearchItemsToWatchByIdUseCase(): SearchItemToWatchUseCase =
        SearchItemToWatchUseCase()

    @Provides
    @Singleton
    fun provideDeleteItemsToWatchByIdUseCase(): DeleteItemToWatchByIdUseCase =
        DeleteItemToWatchByIdUseCase()

    @Provides
    @Singleton
    fun provideDatabaseDriverFactory(@ApplicationContext appContext: Context): DatabaseDriverFactory =
        DatabaseDriverFactory(context = appContext)

    @Provides
    @Singleton
    fun providerDbSDK(databaseFactory: DatabaseDriverFactory) = MovieDataSkd(databaseFactory)
}