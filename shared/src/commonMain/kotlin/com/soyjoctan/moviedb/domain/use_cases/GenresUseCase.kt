package com.soyjoctan.moviedb.domain.use_cases

import com.soyjoctan.moviedb.data.model.dtos.WrapperStatusInfo
import com.soyjoctan.moviedb.data.model.dtos.genres.GenresDTO
import com.soyjoctan.moviedb.data.repository.Repository
import com.soyjoctan.moviedb.presentation.models.GenreModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GenresUseCase {
    private val repository: Repository = Repository()

    operator fun invoke(): Flow<WrapperStatusInfo> = flow {
        basicValidationResponse<GenresDTO>(repository.getGenres()).collect {
            when (it) {
                is WrapperStatusInfo.SuccessResponse<*> -> {
                    val results: ArrayList<GenreModel> = arrayListOf()

                    (it.response as GenresDTO).genres?.forEach { genre ->
                        results.add(
                            GenreModel(
                                id = genre.id,
                                name = genre.name
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