package com.soyjoctan.moviedb.android.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soyjoctan.moviedb.data.model.WrapperStatusRequest
import com.soyjoctan.moviedb.data.model.genres.Genre
import com.soyjoctan.moviedb.data.repository.Repository
import com.soyjoctan.moviedb.domain.use_cases.TopRatedUseCase
import com.soyjoctan.moviedb.presentation.models.TopRatedModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val topRatedMoviesUseCase: TopRatedUseCase
) : ViewModel() {
    private var _genresMutableLiveData: MutableLiveData<List<Genre>> = MutableLiveData()
    val listGenresObservable: LiveData<List<Genre>> = _genresMutableLiveData

    private var _topRatedModelMutableLiveData: MutableLiveData<ArrayList<TopRatedModel>> = MutableLiveData()
    val listTopRatedModelMoviesObservable: LiveData<ArrayList<TopRatedModel>> = _topRatedModelMutableLiveData

    private val repository: Repository = Repository()
    var genreSelected: Genre? = null

    fun getGenres() {
        viewModelScope.launch {
            _genresMutableLiveData.value = repository.getGenres().genres!!
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
                        _topRatedModelMutableLiveData.value = it.response as ArrayList<TopRatedModel>
                    }
                    else -> {}
                }
            }
        }
    }
}