package com.soyjoctan.moviedb.domain.use_cases

import com.soyjoctan.moviedb.data.model.WrapperStatusRequest
import com.soyjoctan.moviedb.data.model.toprated.TopRatedDTO
import com.soyjoctan.moviedb.data.repository.Repository
import com.soyjoctan.moviedb.presentation.models.TopRatedModel
import io.ktor.client.statement.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TopRatedUseCase {
    private val repository: Repository = Repository()

    operator fun invoke(): Flow<WrapperStatusRequest> = flow {
        val response: HttpResponse = repository.getTopRated()

        basicValidationResponse<TopRatedDTO>(response).collect {
            when (it) {
                is WrapperStatusRequest.SuccessResponse<*> -> {
                    val results: ArrayList<TopRatedModel> = arrayListOf()

                    (it.response as TopRatedDTO).results?.forEach { movie ->
                        results.add(
                            TopRatedModel(
                                movieName = movie.title ?: "Sin título",
                                posterPathImage = movie.posterPath ?: "Sin imágen",
                                popularity = movie.popularity ?: 0.0,
                                movieId = movie.id
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
