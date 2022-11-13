package com.soyjoctan.moviedb.domain.use_cases

import com.soyjoctan.moviedb.data.model.dtos.WrapperStatusInfo
import com.soyjoctan.moviedb.data.model.dtos.toprated.TopRatedDTO
import com.soyjoctan.moviedb.data.repository.Repository
import com.soyjoctan.moviedb.presentation.models.TopRatedModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TopRatedUseCase {
    private val repository: Repository = Repository()

    operator fun invoke(): Flow<WrapperStatusInfo> = flow {
        basicValidationResponse<TopRatedDTO>(repository.getTopRated()).collect {
            when (it) {
                is WrapperStatusInfo.SuccessResponse<*> -> {
                    val results: ArrayList<TopRatedModel> = arrayListOf()

                    (it.response as TopRatedDTO).results?.forEach { movie ->
                        results.add(
                            TopRatedModel(
                                itemName = movie.title ?: "Sin título",
                                posterPathImage = movie.posterPath ?: "Sin imágen",
                                popularity = movie.popularity ?: 0.0,
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
