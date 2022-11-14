package com.soyjoctan.moviedb.domain.use_cases

import SearchItemDTO
import com.soyjoctan.moviedb.data.model.dtos.WrapperStatusInfo
import com.soyjoctan.moviedb.data.repository.Repository
import com.soyjoctan.moviedb.presentation.models.ClassBaseItemModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SearchItemUseCase {
    private val repository: Repository = Repository()

    operator fun invoke(
        page: Long = 1,
        query: String,
        currentListItems: ArrayList<ClassBaseItemModel>?
    ): Flow<WrapperStatusInfo> = flow {
        basicValidationResponse<SearchItemDTO>(
            repository.searchItems(
                page,
                query
            )
        ).collect { it: WrapperStatusInfo ->
            when (it) {
                is WrapperStatusInfo.SuccessResponse<*> -> {
                    val result: ArrayList<ClassBaseItemModel> = arrayListOf()

                    (it.response as SearchItemDTO).results?.forEach { item ->
                        result.add(
                            ClassBaseItemModel(
                                itemName = item.name ?: item.originalTitle ?: item.originalName,
                                posterPathImage = item.posterPath ?: item.profilePath,
                                popularity = null,
                                itemId = item.id,
                                backdropPath = item.backdropPath
                            )
                        )
                    }

                    val listItems = verifyInfoAppend(currentListItems, result)

                    emit(WrapperStatusInfo.SuccessResponse(listItems))
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