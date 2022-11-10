package com.soyjoctan.moviedb.android.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soyjoctan.moviedb.android.presentation.models.CarouselModel
import com.soyjoctan.moviedb.data.model.WrapperStatusRequest
import com.soyjoctan.moviedb.domain.use_cases.*
import com.soyjoctan.moviedb.presentation.models.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val genresUseCase: GenresUseCase,
    private val topRatedMoviesUseCase: TopRatedUseCase,
    private val upcomingUseCase: UpcomingMovieUseCase,
    private val findMoviesByGenreUseCase: FindMoviesByGenreUseCase,
    private val moveDetailsUseCase: DetailMoviesUseCase
) : ViewModel() {
    // Lista de géneros
    private var _genresMutableLiveData: MutableLiveData<List<GenreModel>> = MutableLiveData()
    val listGenresObservable: LiveData<List<GenreModel>> = _genresMutableLiveData

    // Películas mejor ranqueadas
    private var _topRatedModelMutableLiveData: MutableLiveData<ArrayList<TopRatedModel>> =
        MutableLiveData()
    val listTopRatedModelMoviesObservable: LiveData<ArrayList<TopRatedModel>> =
        _topRatedModelMutableLiveData

    // Películas próximas a salir
    private var _upcomingMoviesModelMutableLiveData: MutableLiveData<ArrayList<UpcomingMoviesModel>> =
        MutableLiveData()
    val listUpcomingMoviesModelMoviesObservable: LiveData<ArrayList<UpcomingMoviesModel>> =
        _upcomingMoviesModelMutableLiveData

    // Listado de películas por género
    private var _moviesByGenreModelMutableLiveData: MutableLiveData<ArrayList<FindByGenreModel>> =
        MutableLiveData()
    val moviesByGenreModelMutableLiveDataObservable: LiveData<ArrayList<FindByGenreModel>> =
        _moviesByGenreModelMutableLiveData


    // Detalle de la película
    private var _detailsMovieMutableLiveData: MutableLiveData<DetailsMovieModel> =
        MutableLiveData()
    val detailsMovieMutableLiveDataObservable: LiveData<DetailsMovieModel> =
        _detailsMovieMutableLiveData


    // Observers para comunicación de datos
    val movieDetailsSelected: MutableLiveData<CarouselModel> = MutableLiveData()

    fun getGenres() {
        viewModelScope.launch {
            genresUseCase().collect {
                when (it) {
                    is WrapperStatusRequest.loading -> {
                        _genresMutableLiveData.value = null
                    }
                    is WrapperStatusRequest.SuccessResponse<*> -> {
                        _genresMutableLiveData.value =
                            it.response as ArrayList<GenreModel>
                    }
                    else -> {}
                }
            }
        }
    }

    fun getTopRatedMovies() {
        viewModelScope.launch {
            topRatedMoviesUseCase().collect {
                when (it) {
                    is WrapperStatusRequest.loading -> {
                        _topRatedModelMutableLiveData.value = null
                    }
                    is WrapperStatusRequest.SuccessResponse<*> -> {
                        _topRatedModelMutableLiveData.value =
                            it.response as ArrayList<TopRatedModel>
                    }
                    else -> {}
                }
            }
        }
    }


    fun getUpcomingMoviesList() {
        viewModelScope.launch {
            upcomingUseCase().collect {
                when (it) {
                    is WrapperStatusRequest.loading -> {
                        _upcomingMoviesModelMutableLiveData.value = null
                    }
                    is WrapperStatusRequest.SuccessResponse<*> -> {
                        _upcomingMoviesModelMutableLiveData.value =
                            it.response as ArrayList<UpcomingMoviesModel>
                    }
                    else -> {}
                }
            }
        }
    }

    fun getMoviesByGenre(
        idGenre: Long,
        page: Long,
        currentListItems: ArrayList<FindByGenreModel>?
    ) {
        viewModelScope.launch {
            findMoviesByGenreUseCase(idGenre, page, currentListItems).collect {
                when (it) {
                    is WrapperStatusRequest.loading -> {
                        _moviesByGenreModelMutableLiveData.value = null
                    }
                    is WrapperStatusRequest.SuccessResponse<*> -> {
                        _moviesByGenreModelMutableLiveData.value =
                            it.response as ArrayList<FindByGenreModel>
                    }
                    else -> {}
                }
            }
        }
    }

    fun getMovieDetailsById(movieId: Long) {
        viewModelScope.launch {
            moveDetailsUseCase(movieId).collect {
                when (it) {
                    is WrapperStatusRequest.loading -> {
                        _detailsMovieMutableLiveData.value = null
                    }
                    is WrapperStatusRequest.SuccessResponse<*> -> {
                        _detailsMovieMutableLiveData.value =
                            it.response as DetailsMovieModel
                    }
                    else -> {}
                }
            }
        }
    }
}