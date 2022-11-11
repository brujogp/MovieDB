package com.soyjoctan.moviedb.domain.use_cases

import com.soyjoctan.moviedb.data.model.WrapperStatusRequest
import com.soyjoctan.moviedb.data.model.findbygenre.FindByGenreDTO
import com.soyjoctan.moviedb.data.model.findbygenre.Result
import com.soyjoctan.moviedb.data.repository.Repository
import com.soyjoctan.moviedb.presentation.models.FindByGenreModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FindMoviesByGenreUseCase {
    private val repository: Repository = Repository()

    operator fun invoke(
        genreId: Long,
        page: Long,
        currentListItems: ArrayList<FindByGenreModel>?
    ): Flow<WrapperStatusRequest> = flow {
        basicValidationResponse<FindByGenreDTO>(
            repository.getMoviesByGenre(
                genreId,
                page
            )
        ).collect {
            when (it) {
                is WrapperStatusRequest.SuccessResponse<*> -> {
                    val results: ArrayList<FindByGenreModel> = arrayListOf()

                    (it.response as FindByGenreDTO).results?.forEach { item: Result ->
                        results.add(
                            FindByGenreModel(
                                itemId = item.id,
                                itemName = item.title,
                                posterPathImage = item.posterPath,
                                popularity = null,
                                backdropPath = item.backdropPath
                            )
                        )
                    }

                    val listItems = verifyInfoAppend(currentListItems, results)

                    emit(
                        WrapperStatusRequest.SuccessResponse(
                            listItems
                        )
                    )
                }
                else -> {
                    emit(it)
                }
            }
        }
    }

    private fun verifyInfoAppend(
        currentListItems: ArrayList<FindByGenreModel>?,
        newListItems: ArrayList<FindByGenreModel>
    ): ArrayList<FindByGenreModel> {
        val finalListItems: ArrayList<FindByGenreModel> =
            if (currentListItems?.isNotEmpty() == true) {
                currentListItems.addAll(newListItems)
                currentListItems
            } else {
                newListItems
            }

        return finalListItems
    }
}