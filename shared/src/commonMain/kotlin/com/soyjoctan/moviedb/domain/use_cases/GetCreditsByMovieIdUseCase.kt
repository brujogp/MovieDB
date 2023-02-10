package com.soyjoctan.moviedb.domain.use_cases


import com.soyjoctan.moviedb.data.model.dtos.WrapperStatusInfo
import com.soyjoctan.moviedb.presentation.models.CrewTypes.*
import com.soyjoctan.moviedb.data.model.dtos.credits.CreditsDTO
import com.soyjoctan.moviedb.data.repository.Repository
import com.soyjoctan.moviedb.presentation.models.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetCreditsByMovieIdUseCase {
    private val repository: Repository = Repository()

    operator fun invoke(
        movieId: Long,
    ): Flow<WrapperStatusInfo> = flow {
        basicValidationResponse<CreditsDTO>(
            repository.getCreditsByMovieId(
                movieId
            )
        ).collect {
            when (it) {
                is WrapperStatusInfo.SuccessResponse<*> -> {
                    val result = MovieCreditsModel()

                    (it.response as CreditsDTO).cast?.forEach { item ->
                        result.cast.add(
                            Cast(
                                itemId = item.castID,
                                adult = item.adult,
                                gender = if (item.gender?.toInt() == 2) "Actor" else "Actriz",
                                character = item.character,
                                name = "${item.name}${if (item.originalName?.isNotEmpty() == true) " (${item.originalName})" else ""}",
                                posterPathImage = item.profilePath,
                                itemName = "${item.name}${if (item.originalName?.isNotEmpty() == true) " (${item.character})" else ""}",
                                popularity = PARAMS.Actors,
                                backdropPath = null
                            )
                        )
                    }

                    it.response.crew?.forEach { item ->
                        result.crew.add(
                            Crew(
                                department = item.department,
                                job = item.job,
                                name = "${item.name}",
                                originalName = item.originalName ?: item.name
                            )
                        )
                    }
                    filterCrew(result)
                    emit(WrapperStatusInfo.SuccessResponse(result))
                }
                else -> {
                    emit(it)
                }
            }
        }
    }

    private fun filterCrew(result: MovieCreditsModel) {
        result.crew.forEach {
            when (it.department) {
                DIRECTOR.type -> {
                    result.directors.add(
                        Crew(
                            department = it.department,
                            job = it.job,
                            name = it.name
                        )
                    )
                }
            }
        }
    }

    companion object PARAMS{
        const val Actors = 1111.0
    }
}