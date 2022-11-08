package com.soyjoctan.moviedb.android.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soyjoctan.moviedb.android.data.network.RequestStatus
import com.soyjoctan.moviedb.android.domain.models.TopRated
import com.soyjoctan.moviedb.android.domain.usecases.TopRatedUseCase
import com.soyjoctan.moviedb.model.genres.Genre
import com.soyjoctan.moviedb.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val topRatedMoviesUseCase: TopRatedUseCase
) : ViewModel() {
    private var _genresMutableLiveData: MutableLiveData<List<Genre>> = MutableLiveData()
    val listGenresObservable: LiveData<List<Genre>> = _genresMutableLiveData

    private var _topRatedMutableLiveData: MutableLiveData<ArrayList<TopRated>> = MutableLiveData()
    val listTopRatedMoviesObservable: LiveData<ArrayList<TopRated>> = _topRatedMutableLiveData

    private val repository: Repository = Repository()
    var genreSelected: Genre? = null

    fun getGenres() {
        viewModelScope.launch {
            _genresMutableLiveData.value = repository.getGenres().genres!!
        }
    }

    fun getTopRatedMovies() {
        viewModelScope.launch {
            topRatedMoviesUseCase().collect() {
                when (it) {
                    is RequestStatus.loading -> {
                        Log.d("TEST-T", "Cargando")
                    }
                    is RequestStatus.SuccessResponse<*> -> {
                        _topRatedMutableLiveData.value = it.response as ArrayList<TopRated>
                    }
                    else -> {}
                }
            }
        }
    }
}