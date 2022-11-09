package com.soyjoctan.moviedb.domain.use_cases

import com.soyjoctan.moviedb.data.model.WrapperStatusRequest
import com.soyjoctan.moviedb.data.model.findbygenre.FindByGenreDTO
import com.soyjoctan.moviedb.data.model.findbygenre.Result
import com.soyjoctan.moviedb.data.model.genres.GenresDTO
import com.soyjoctan.moviedb.data.repository.Repository
import com.soyjoctan.moviedb.presentation.models.FindByGenreModel
import com.soyjoctan.moviedb.presentation.models.GenreModel
import io.ktor.client.statement.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FindMoviesByGenreUseCase {
    private val repository: Repository = Repository()

    operator fun invoke(genreId: Long): Flow<WrapperStatusRequest> = flow {
        val response: HttpResponse = repository.getMoviesByGenre(genreId)

        basicValidationResponse<FindByGenreDTO>(response).collect {
            when (it) {
                is WrapperStatusRequest.SuccessResponse<*> -> {
                    val results: ArrayList<FindByGenreModel> = arrayListOf()

                    (it.response as FindByGenreDTO).results?.forEach { item: Result ->
                        results.add(
                            FindByGenreModel(
                                movieId = item.id,
                                movieName = item.title,
                                posterPathImage = item.posterPath,
                                popularity = null,
                                backdropPath = item.backdropPath
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