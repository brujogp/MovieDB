package com.soyjoctan.moviedb.domain.use_cases

import com.soyjoctan.moviedb.data.model.WrapperStatusRequest
import com.soyjoctan.moviedb.data.model.moviedetails.MovieDetailsDTO
import com.soyjoctan.moviedb.data.repository.Repository
import com.soyjoctan.moviedb.presentation.models.*
import io.ktor.client.statement.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DetailMoviesUseCase {
    private val repository: Repository = Repository()

    operator fun invoke(movieId: Long): Flow<WrapperStatusRequest> = flow {
        basicValidationResponse<MovieDetailsDTO>(repository.getMovieDetailById(movieId)).collect {
            when (it) {
                is WrapperStatusRequest.SuccessResponse<*> -> {
                    (it.response as MovieDetailsDTO).apply {
                        val productionCompaniesList: ArrayList<ProductionCompany> = arrayListOf()
                        productionCompanies?.forEach { itemCompany ->
                            productionCompaniesList.add(
                                ProductionCompany(
                                    logoPath = itemCompany.logoPath,
                                    name = itemCompany.name
                                )
                            )
                        }

                        val productionCountriesList: ArrayList<ProductionCountry> = arrayListOf()
                        productionCountries?.forEach { itemCountry ->
                            productionCountriesList.add(
                                ProductionCountry(
                                    name = itemCountry.name
                                )
                            )
                        }

                        val spokenLanguagesList: ArrayList<SpokenLanguage> = arrayListOf()
                        spokenLanguages?.forEach { itemLanguages ->
                            spokenLanguagesList.add(
                                SpokenLanguage(
                                    name = itemLanguages.name
                                )
                            )
                        }

                        val result = DetailsMovieModel(
                            backdropPath = backdropPath,
                            posterPathImage = posterPath,
                            popularity = popularity,
                            movieId = movieId,
                            movieName = title,
                            budget = budget,
                            genres = genres,
                            homepage = homepage,
                            originalLanguage = originalLanguage,
                            originalTitle = originalTitle,
                            overview = if (overview.isNullOrEmpty()) "Aún no se tiene sinópsis para el idioma Español. Por favor cambia de idioma al Inglés e intenta de nuevo." else overview,
                            releaseDate = releaseDate,
                            voteAverage = voteAverage,
                            productionCompanies = productionCompaniesList,
                            productionCountries = productionCountriesList,
                            spokenLanguages = spokenLanguagesList
                        )

                        emit(
                            WrapperStatusRequest.SuccessResponse(
                                result
                            )
                        )
                    }
                }
                else -> {
                    emit(it)
                }
            }
        }
    }
}