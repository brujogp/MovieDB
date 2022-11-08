package com.soyjoctan.moviedb.android.di.modules

import com.soyjoctan.moviedb.domain.use_cases.GenresUseCase
import com.soyjoctan.moviedb.domain.use_cases.TopRatedUseCase
import com.soyjoctan.moviedb.domain.use_cases.UpcomingMovieUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
}