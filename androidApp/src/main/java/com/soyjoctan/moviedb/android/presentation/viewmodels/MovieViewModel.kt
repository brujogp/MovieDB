package com.soyjoctan.moviedb.android.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soyjoctan.moviedb.data.model.dtos.WrapperStatusInfo
import com.soyjoctan.moviedb.data.model.entities.ItemToWatch
import com.soyjoctan.moviedb.domain.use_cases.*
import com.soyjoctan.moviedb.presentation.models.*
import com.soyjoctan.moviedb.shared.cache.ItemsToWatch
import com.soyjoctan.moviedb.shared.cache.MovieDataSkd
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val genresUseCase: GenresUseCase,
    private val topRatedMoviesUseCase: TopRatedUseCase,
    private val upcomingUseCase: UpcomingMovieUseCase,
    private val findMoviesByGenreUseCase: FindMoviesByGenreUseCase,
    private val moveDetailsUseCase: DetailMoviesUseCase,
    private val popularTvShowsUseCase: PopularTvShowsUseCase,
    private val itemsForWatchUseCase: ItemsForWatchUseCase,
    private val addItemsForWatchUseCase: AddItemForWatchUseCase,

    private val dbSdk: MovieDataSkd
) : ViewModel() {
    // Lista de géneros
    private var _genresMutableLiveData: MutableLiveData<List<GenreModel>> = MutableLiveData()
    val listGenresObservable: LiveData<List<GenreModel>> = _genresMutableLiveData

    // Películas mejor ranqueadas
    private var _topRatedModelMutableLiveData: MutableLiveData<ArrayList<ClassBaseItemModel>> =
        MutableLiveData()
    val listTopRatedModelMoviesObservable: LiveData<ArrayList<ClassBaseItemModel>> =
        _topRatedModelMutableLiveData

    // Películas próximas a salir
    private var _upcomingMoviesModelMutableLiveData: MutableLiveData<ArrayList<ClassBaseItemModel>> =
        MutableLiveData()
    val listUpcomingMoviesModelMoviesObservable: LiveData<ArrayList<ClassBaseItemModel>> =
        _upcomingMoviesModelMutableLiveData

    // Listado de películas por género
    private var _moviesByGenreModelMutableLiveData: MutableLiveData<ArrayList<ClassBaseItemModel>> =
        MutableLiveData()
    val moviesByGenreModelMutableLiveDataObservable: LiveData<ArrayList<ClassBaseItemModel>> =
        _moviesByGenreModelMutableLiveData

    // Detalle de la película
    private var _detailsMovieMutableLiveData: MutableLiveData<DetailsMovieModel> =
        MutableLiveData()
    val detailsMovieLiveDataObservable: LiveData<DetailsMovieModel> =
        _detailsMovieMutableLiveData


    // Lista de series populares
    private var _popularTvShowsListMutableLiveData: MutableLiveData<ArrayList<ClassBaseItemModel>> =
        MutableLiveData()
    val popularTvShowsListLiveDataObservable: LiveData<ArrayList<ClassBaseItemModel>> =
        _popularTvShowsListMutableLiveData

    // Lista de series populares
    private var _itemsToWatchListMutableLiveData: MutableLiveData<ArrayList<ItemsToWatch>> =
        MutableLiveData()
    val itemsToWatchListLiveDataObservable: LiveData<ArrayList<ItemsToWatch>> =
        _itemsToWatchListMutableLiveData

    // Observers para comunicación de datos
    val itemDetailsSelected: MutableLiveData<ClassBaseItemModel> = MutableLiveData()
    val detailsOfItemSelected: MutableLiveData<DetailsMovieModel> = MutableLiveData()

    fun getGenres() {
        viewModelScope.launch {
            genresUseCase().collect {
                when (it) {
                    is WrapperStatusInfo.Loading -> {
                        _genresMutableLiveData.value = null
                    }
                    is WrapperStatusInfo.SuccessResponse<*> -> {
                        _genresMutableLiveData.value =
                            it.response as ArrayList<GenreModel>
                    }
                    is WrapperStatusInfo.NoInternetConnection -> {
                        _genresMutableLiveData.value = null
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
                    is WrapperStatusInfo.Loading -> {
                        _topRatedModelMutableLiveData.value = null
                    }
                    is WrapperStatusInfo.SuccessResponse<*> -> {
                        _topRatedModelMutableLiveData.value =
                            it.response as ArrayList<ClassBaseItemModel>
                    }
                    is WrapperStatusInfo.NoInternetConnection -> {
                        _topRatedModelMutableLiveData.value = null
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
                    is WrapperStatusInfo.Loading -> {
                        _upcomingMoviesModelMutableLiveData.value = null
                    }
                    is WrapperStatusInfo.SuccessResponse<*> -> {
                        _upcomingMoviesModelMutableLiveData.value =
                            it.response as ArrayList<ClassBaseItemModel>
                    }
                    is WrapperStatusInfo.NoInternetConnection -> {
                        _upcomingMoviesModelMutableLiveData.value = null
                    }
                    else -> {}
                }
            }
        }
    }

    fun getMoviesByGenre(
        idGenre: Long,
        page: Long,
        currentListItems: ArrayList<ClassBaseItemModel>?
    ) {
        viewModelScope.launch {
            findMoviesByGenreUseCase(idGenre, page, currentListItems).collect {
                when (it) {
                    is WrapperStatusInfo.Loading -> {
                        _moviesByGenreModelMutableLiveData.value = null
                    }
                    is WrapperStatusInfo.SuccessResponse<*> -> {
                        _moviesByGenreModelMutableLiveData.value =
                            it.response as ArrayList<ClassBaseItemModel>
                    }
                    is WrapperStatusInfo.NoInternetConnection -> {
                        _moviesByGenreModelMutableLiveData.value = null
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
                    is WrapperStatusInfo.Loading -> {
                        _detailsMovieMutableLiveData.value = null
                    }
                    is WrapperStatusInfo.SuccessResponse<*> -> {
                        _detailsMovieMutableLiveData.value =
                            it.response as DetailsMovieModel
                    }
                    is WrapperStatusInfo.NoInternetConnection -> {
                        _detailsMovieMutableLiveData.value = null
                    }
                    else -> {}
                }
            }
        }
    }

    fun getPopularTvShows() {
        viewModelScope.launch {
            popularTvShowsUseCase().collect {
                when (it) {
                    is WrapperStatusInfo.Loading -> {
                        _popularTvShowsListMutableLiveData.value = null
                    }
                    is WrapperStatusInfo.SuccessResponse<*> -> {
                        _popularTvShowsListMutableLiveData.value =
                            it.response as ArrayList<ClassBaseItemModel>
                    }
                    is WrapperStatusInfo.NoInternetConnection -> {
                        _popularTvShowsListMutableLiveData.value = null
                    }
                    else -> {}
                }
            }
        }
    }

    fun getItemsToWatch() {
        viewModelScope.launch {
            itemsForWatchUseCase.invoke(sdk = dbSdk).collect {
                when (it) {
                    is WrapperStatusInfo.Loading -> {
                        _itemsToWatchListMutableLiveData.value = null
                    }
                    is WrapperStatusInfo.SuccessResponse<*> -> {
                        _itemsToWatchListMutableLiveData.value =
                            it.response as ArrayList<ItemsToWatch>
                    }
                    is WrapperStatusInfo.NoInternetConnection -> {
                        _itemsToWatchListMutableLiveData.value = null
                    }
                    else -> {}
                }
            }
        }
    }


    fun addItemToWatch(itemToWatch: ItemToWatch) {
        viewModelScope.launch {
            addItemsForWatchUseCase.invoke(itemToWatch = itemToWatch, sdk = dbSdk)
        }
    }
}