package com.soyjoctan.moviedb.domain.use_cases

import com.soyjoctan.moviedb.data.model.WrapperStatusRequest
import com.soyjoctan.moviedb.data.model.toprated.TopRatedDTO
import com.soyjoctan.moviedb.data.model.upcoming.UpComingMoviesDTO
import com.soyjoctan.moviedb.data.repository.Repository
import com.soyjoctan.moviedb.presentation.models.TopRatedModel
import com.soyjoctan.moviedb.presentation.models.UpcomingMoviesModel
import io.ktor.client.statement.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UpcomingMovieUseCase {
    private val repository: Repository = Repository()

    operator fun invoke(): Flow<WrapperStatusRequest> = flow {
        val response: HttpResponse = repository.getUpcomingMovies()

        basicValidationResponse<UpComingMoviesDTO>(response).collect {
            when (it) {
                is WrapperStatusRequest.SuccessResponse<*> -> {
                    val results: ArrayList<UpcomingMoviesModel> = arrayListOf()

                    (it.response as UpComingMoviesDTO).results?.forEach { movie ->
                        results.add(
                            UpcomingMoviesModel(
                                movieName = movie.title ?: "Sin título",
                                posterPathImage = movie.posterPath ?: "Sin imágen",
                                popularity = null,
                                movieId = movie.id,
                                backdropPath = movie.backdropPath
                            )
                        )
                    }
                    emit(
                        WrapperStatusRequest.SuccessResponse(
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