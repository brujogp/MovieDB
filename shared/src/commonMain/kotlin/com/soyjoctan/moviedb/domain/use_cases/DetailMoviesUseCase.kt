package com.soyjoctan.moviedb.domain.use_cases

import com.soyjoctan.moviedb.data.model.dtos.WrapperStatusInfo
import com.soyjoctan.moviedb.data.model.dtos.moviedetails.MovieDetailsDTO
import com.soyjoctan.moviedb.data.repository.Repository
import com.soyjoctan.moviedb.presentation.models.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DetailMoviesUseCase {
    private val repository: Repository = Repository()

    operator fun invoke(movieId: Long): Flow<WrapperStatusInfo> = flow {
        basicValidationResponse<MovieDetailsDTO>(repository.getMovieDetailById(movieId)).collect {
            when (it) {
                is WrapperStatusInfo.SuccessResponse<*> -> {
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

                        val newGenresList: ArrayList<GenreModel> = arrayListOf()

                        val result = DetailsMovieModel(
                            backdropPath = backdropPath,
                            posterPathImage = posterPath,
                            popularity = popularity,
                            itemId = movieId,
                            itemName = title,
                            budget = budget,
                            genres = null,
                            homepage = homepage,
                            originalLanguage = originalLanguage,
                            originalTitle = originalTitle,
                            overview = if (overview.isNullOrEmpty()) "Aún no se tiene sinópsis para el idioma Español. Por favor cambia de idioma al Inglés e intenta de nuevo." else overview,
                            releaseDate = releaseDate,
                            voteAverage = voteAverage,
                            productionCompanies = productionCompaniesList,
                            productionCountries = productionCountriesList,
                            spokenLanguages = spokenLanguagesList,
                            revenue = revenue
                        )
                        genres?.forEach { g ->
                            newGenresList.add(GenreModel(g.id, g.name))
                        }

                        result.genres = newGenresList

                        emit(
                            WrapperStatusInfo.SuccessResponse(
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