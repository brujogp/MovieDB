package com.soyjoctan.moviedb.domain.use_cases

import com.soyjoctan.moviedb.data.model.dtos.WrapperStatusInfo
import com.soyjoctan.moviedb.data.model.dtos.upcoming.UpComingMoviesDTO
import com.soyjoctan.moviedb.data.repository.Repository
import com.soyjoctan.moviedb.presentation.models.ClassBaseItemModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UpcomingMovieUseCase {
    private val repository: Repository = Repository()

    operator fun invoke(): Flow<WrapperStatusInfo> = flow {
        basicValidationResponse<UpComingMoviesDTO>(repository.getUpcomingMovies()).collect {
            when (it) {
                is WrapperStatusInfo.SuccessResponse<*> -> {
                    val results: ArrayList<ClassBaseItemModel> = arrayListOf()

                    (it.response as UpComingMoviesDTO).results?.forEach { movie ->
                        results.add(
                            ClassBaseItemModel(
                                itemName = movie.title ?: "Sin título",
                                posterPathImage = movie.posterPath ?: "Sin imágen",
                                popularity = null,
                                itemId = movie.id,
                                backdropPath = movie.backdropPath
                            )
                        )
                    }
                    emit(
                        WrapperStatusInfo.SuccessResponse(
                            results
                        )
                    )
                }
                else -> {
                    emit(it)
                }
            }
        }
    }
}