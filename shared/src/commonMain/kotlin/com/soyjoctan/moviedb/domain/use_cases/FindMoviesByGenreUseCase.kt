package com.soyjoctan.moviedb.domain.use_cases

import com.soyjoctan.moviedb.data.model.dtos.WrapperStatusInfo
import com.soyjoctan.moviedb.data.model.dtos.findbygenre.FindByGenreDTO
import com.soyjoctan.moviedb.data.model.dtos.findbygenre.Result
import com.soyjoctan.moviedb.data.repository.Repository
import com.soyjoctan.moviedb.presentation.models.ClassBaseItemModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FindMoviesByGenreUseCase {
    private val repository: Repository = Repository()

    operator fun invoke(
        genreId: Long,
        page: Long,
        currentListItems: ArrayList<ClassBaseItemModel>?
    ): Flow<WrapperStatusInfo> = flow {
        basicValidationResponse<FindByGenreDTO>(
            repository.getMoviesByGenre(
                genreId,
                page
            )
        ).collect {
            when (it) {
                is WrapperStatusInfo.SuccessResponse<*> -> {
                    val results: ArrayList<ClassBaseItemModel> = arrayListOf()

                    (it.response as FindByGenreDTO).results?.forEach { item: Result ->
                        results.add(
                            ClassBaseItemModel(
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
                        WrapperStatusInfo.SuccessResponse(
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
        currentListItems: ArrayList<ClassBaseItemModel>?,
        newListItems: ArrayList<ClassBaseItemModel>
    ): ArrayList<ClassBaseItemModel> {
        val finalListItems: ArrayList<ClassBaseItemModel> =
            if (currentListItems?.isNotEmpty() == true) {
                currentListItems.addAll(newListItems)
                currentListItems
            } else {
                newListItems
            }

        return finalListItems
    }
}