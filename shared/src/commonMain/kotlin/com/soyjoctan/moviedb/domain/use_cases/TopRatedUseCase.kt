package com.soyjoctan.moviedb.domain.use_cases

import com.soyjoctan.moviedb.data.model.dtos.WrapperStatusInfo
import com.soyjoctan.moviedb.data.model.dtos.toprated.TopRatedDTO
import com.soyjoctan.moviedb.data.repository.Repository
import com.soyjoctan.moviedb.presentation.models.ClassBaseItemModel
import kotlinx.coroutines.flow.flow

class TopRatedUseCase {
    private val repository: Repository = Repository()

    operator fun invoke(
        page: Long,
        currentListItems: ArrayList<ClassBaseItemModel>?
    ) = flow {
        basicValidationResponse<TopRatedDTO>(repository.getTopRated(page)).collect {
            when (it) {
                is WrapperStatusInfo.SuccessResponse<*> -> {
                    val results: ArrayList<ClassBaseItemModel> = arrayListOf()

                    (it.response as TopRatedDTO).results?.forEach { movie ->
                        results.add(
                            ClassBaseItemModel(
                                itemName = movie.title ?: "Sin título",
                                posterPathImage = movie.posterPath ?: "Sin imágen",
                                popularity = movie.popularity ?: 0.0,
                                itemId = movie.id,
                                backdropPath = movie.backdropPath
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
