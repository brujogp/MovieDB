package com.soyjoctan.moviedb.android.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soyjoctan.moviedb.data.model.WrapperStatusRequest
import com.soyjoctan.moviedb.domain.use_cases.FindMoviesByGenreUseCase
import com.soyjoctan.moviedb.domain.use_cases.GenresUseCase
import com.soyjoctan.moviedb.domain.use_cases.TopRatedUseCase
import com.soyjoctan.moviedb.domain.use_cases.UpcomingMovieUseCase
import com.soyjoctan.moviedb.presentation.models.FindByGenreModel
import com.soyjoctan.moviedb.presentation.models.GenreModel
import com.soyjoctan.moviedb.presentation.models.TopRatedModel
import com.soyjoctan.moviedb.presentation.models.UpcomingMoviesModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val genresUseCase: GenresUseCase,
    private val topRatedMoviesUseCase: TopRatedUseCase,
    private val upcomingUseCase: UpcomingMovieUseCase,
    private val findMoviesByGenreUseCase: FindMoviesByGenreUseCase
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

    fun getGenres() {
        viewModelScope.launch {
            genresUseCase().collect {
                when (it) {
                    is WrapperStatusRequest.loading -> {
                        Log.d("TEST-T", "Cargando")
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
                        Log.d("TEST-T", "Cargando")
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
                        Log.d("TEST-T", "Cargando")
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

    fun getMoviesByGenre(idGenre: Long) {
        viewModelScope.launch {
            findMoviesByGenreUseCase(idGenre).collect {
                when (it) {
                    is WrapperStatusRequest.loading -> {
                        Log.d("TEST-T", "Cargando")
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
}