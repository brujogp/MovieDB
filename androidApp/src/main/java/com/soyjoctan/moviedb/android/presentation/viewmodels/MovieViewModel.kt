package com.soyjoctan.moviedb.android.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soyjoctan.moviedb.data.model.dtos.WrapperStatusInfo
import com.soyjoctan.moviedb.domain.use_cases.*
import com.soyjoctan.moviedb.presentation.models.*
import com.soyjoctan.moviedb.shared.cache.ItemsLiked
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
    private val searchItemsUseCase: SearchItemUseCase,
    private val searchItemToWatchUseCase: SearchItemToWatchUseCase,
    private val deleteItemsToWatchByIdUseCase: DeleteItemToWatchByIdUseCase,
    private val getCreditsByMovieIdUseCase: GetCreditsByMovieIdUseCase,
    private val addItemToLikedListUseCase: AddItemToLikedListUseCase,
    private val searchLikedItemByIdUseCase: SearchLikedItemByIdUseCase,
    private val getLikedItemsUseCase: LikedItemsUseCase,
    private val deleteLikedItemByIdUseCase: DeleteLikedItemByIdUseCase,

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

    // Lista de actores de cierta película
    private var _creditsMovieListMutableLiveData: MutableLiveData<MovieCreditsModel> =
        MutableLiveData()
    val creditsMoviesListLiveDataObservable: LiveData<MovieCreditsModel> =
        _creditsMovieListMutableLiveData

    // Lista de series populares
    private var _searchItemsListMutableLiveData: MutableLiveData<ArrayList<ClassBaseItemModel>> =
        MutableLiveData()
    val searchItemsListLiveDataObservable: LiveData<ArrayList<ClassBaseItemModel>> =
        _searchItemsListMutableLiveData

    // Item de la lista WatchLater buscado en base de datos
    private var _searchItemByIdMutableLiveData: MutableLiveData<ItemsToWatch> =
        MutableLiveData()
    val searchItemToWatchByIdListLiveDataObservable: LiveData<ItemsToWatch> =
        _searchItemByIdMutableLiveData

    // Item de la lista LikedItems buscado en base de datos
    private var _searchLikedItemByIdMutableLiveData: MutableLiveData<ItemsLiked> =
        MutableLiveData()
    val searchLikedItemsToWatchByIdListLiveDataObservable: LiveData<ItemsLiked> =
        _searchLikedItemByIdMutableLiveData

    // Lista de items marcados para ver que quieres ver traidos desde la base de datos
    private var _itemsToWatchListMutableLiveData: MutableLiveData<ArrayList<ItemToWatchModel>> =
        MutableLiveData()
    val itemsToWatchListLiveDataObservable: LiveData<ArrayList<ItemToWatchModel>> =
        _itemsToWatchListMutableLiveData

    // Lista de items marcados como favoritos que quieres ver traidos desde la base de datos
    private var _likedItemsListMutableLiveData: MutableLiveData<ArrayList<ItemLikedModel>> =
        MutableLiveData()
    val likedItemsListMutableLiveData: LiveData<ArrayList<ItemLikedModel>> =
        _likedItemsListMutableLiveData

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

    fun getCreditsByMovieId(movieId: Long) {
        viewModelScope.launch {
            getCreditsByMovieIdUseCase(movieId).collect {
                when (it) {
                    is WrapperStatusInfo.Loading -> {
                        _creditsMovieListMutableLiveData.value = null
                    }
                    is WrapperStatusInfo.SuccessResponse<*> -> {
                        _creditsMovieListMutableLiveData.value =
                            it.response as MovieCreditsModel
                    }
                    is WrapperStatusInfo.NoInternetConnection -> {
                        _creditsMovieListMutableLiveData.value = null
                    }
                    else -> {}
                }
            }
        }
    }

    fun getItemsToWatch(filterByGenres: ArrayList<GenreModel>? = null) {
        viewModelScope.launch {
            itemsForWatchUseCase.invoke(sdk = dbSdk, filterByGenres).collect {
                when (it) {
                    is WrapperStatusInfo.Loading -> {
                        _itemsToWatchListMutableLiveData.value = null
                    }
                    is WrapperStatusInfo.SuccessResponse<*> -> {
                        _itemsToWatchListMutableLiveData.value =
                            it.response as ArrayList<ItemToWatchModel>
                    }
                    is WrapperStatusInfo.NoInternetConnection -> {
                        _itemsToWatchListMutableLiveData.value = null
                    }
                    is WrapperStatusInfo.NotFound -> {
                        _itemsToWatchListMutableLiveData.value = null
                    }
                    else -> {}
                }
            }
        }
    }


    fun getLikedItems(filterByGenres: ArrayList<GenreModel>? = null) {
        viewModelScope.launch {
            getLikedItemsUseCase.invoke(sdk = dbSdk, filterByGenres).collect {
                when (it) {
                    is WrapperStatusInfo.Loading -> {
                        _likedItemsListMutableLiveData.value = null
                    }
                    is WrapperStatusInfo.SuccessResponse<*> -> {
                        _likedItemsListMutableLiveData.value =
                            it.response as ArrayList<ItemLikedModel>
                    }
                    is WrapperStatusInfo.NoInternetConnection -> {
                        _likedItemsListMutableLiveData.value = null
                    }
                    is WrapperStatusInfo.NotFound -> {
                        _likedItemsListMutableLiveData.value = null
                    }
                    else -> {}
                }
            }
        }
    }

    fun searchItems(
        page: Long = 1,
        query: String,
        currentListItems: ArrayList<ClassBaseItemModel>?
    ) {
        viewModelScope.launch {
            searchItemsUseCase.invoke(page, query, currentListItems).collect {
                when (it) {
                    is WrapperStatusInfo.Loading -> {
                        _searchItemsListMutableLiveData.value = null
                    }
                    is WrapperStatusInfo.SuccessResponse<*> -> {
                        _searchItemsListMutableLiveData.value =
                            it.response as ArrayList<ClassBaseItemModel>
                    }
                    is WrapperStatusInfo.NoInternetConnection -> {
                        _searchItemsListMutableLiveData.value = null
                    }
                    else -> {}
                }
            }
        }
    }

    fun searchItemToWatchById(
        itemId: Long
    ) {
        viewModelScope.launch {
            searchItemToWatchUseCase.invoke(dbSdk, itemId).collect {
                when (it) {
                    is WrapperStatusInfo.Loading -> {
                        _searchItemByIdMutableLiveData.value = null
                    }
                    is WrapperStatusInfo.SuccessResponse<*> -> {
                        _searchItemByIdMutableLiveData.value =
                            it.response as ItemsToWatch
                    }
                    is WrapperStatusInfo.NoInternetConnection -> {
                        _searchItemByIdMutableLiveData.value = null
                    }
                    is WrapperStatusInfo.NotFound -> {
                        _searchItemByIdMutableLiveData.value = null
                    }
                    else -> {}
                }
            }
        }
    }

    fun searchLikedItemById(
        itemId: Long
    ) {
        viewModelScope.launch {
            searchLikedItemByIdUseCase.invoke(dbSdk, itemId).collect {
                when (it) {
                    is WrapperStatusInfo.Loading -> {
                        _searchLikedItemByIdMutableLiveData.value = null
                    }
                    is WrapperStatusInfo.SuccessResponse<*> -> {
                        _searchLikedItemByIdMutableLiveData.value =
                            it.response as ItemsLiked
                    }
                    is WrapperStatusInfo.NoInternetConnection -> {
                        _searchLikedItemByIdMutableLiveData.value = null
                    }
                    is WrapperStatusInfo.NotFound -> {
                        _searchLikedItemByIdMutableLiveData.value = null
                    }
                    else -> {}
                }

            }
        }
    }

    fun addItemToWatch(itemToWatch: ItemToWatchModel) {
        viewModelScope.launch {
            addItemsForWatchUseCase.invoke(itemToWatch = itemToWatch, sdk = dbSdk)
        }
    }

    fun addItemToLikedList(itemLiked: ItemLikedModel) {
        viewModelScope.launch {
            addItemToLikedListUseCase.invoke(itemLiked = itemLiked, sdk = dbSdk)
        }
    }

    fun removeItemToWatch(itemToRemove: Long) {
        viewModelScope.launch {
            deleteItemsToWatchByIdUseCase.invoke(sdk = dbSdk, itemToRemove = itemToRemove)
        }
    }

    fun removeLikedItem(itemToRemove: Long) {
        viewModelScope.launch {
            deleteLikedItemByIdUseCase.invoke(sdk = dbSdk, itemToRemove = itemToRemove)
        }
    }
}