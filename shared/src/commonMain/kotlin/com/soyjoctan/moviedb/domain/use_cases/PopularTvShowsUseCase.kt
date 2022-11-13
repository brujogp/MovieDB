package com.soyjoctan.moviedb.domain.use_cases

import com.soyjoctan.moviedb.data.model.dtos.WrapperStatusRequest
import com.soyjoctan.moviedb.data.model.dtos.populartvshows.PopularTvShowsDTO
import com.soyjoctan.moviedb.data.model.dtos.populartvshows.Result
import com.soyjoctan.moviedb.data.repository.Repository
import com.soyjoctan.moviedb.presentation.models.PopularTvShowsModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PopularTvShowsUseCase {
    private val repository: Repository = Repository()

    operator fun invoke(): Flow<WrapperStatusRequest> = flow {
        basicValidationResponse<PopularTvShowsDTO>(repository.getPopularTVShows()).collect {
            when (it) {
                is WrapperStatusRequest.SuccessResponse<*> -> {
                    val results: ArrayList<PopularTvShowsModel> = arrayListOf()

                    (it.response as PopularTvShowsDTO).results?.forEach { tvShow: Result ->
                        results.add(
                            PopularTvShowsModel(
                                itemName = tvShow.name,
                                posterPathImage = tvShow.posterPath,
                                popularity = tvShow.popularity,
                                itemId = tvShow.id,
                                backdropPath = tvShow.backdropPath
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