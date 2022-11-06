package com.soyjoctan.moviedb.android.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soyjoctan.moviedb.model.Genre
import com.soyjoctan.moviedb.repository.Repository
import kotlinx.coroutines.launch

class MovieViewModel : ViewModel() {
    private val repository: Repository = Repository()
    private var _genresMutableLiveData: MutableLiveData<List<Genre>> = MutableLiveData()

    val listGenresObservable: LiveData<List<Genre>> = _genresMutableLiveData

    var genreSelected: Genre? = null

    fun getGenres() {
        viewModelScope.launch {
            _genresMutableLiveData.value = repository.getGenres().genres!!
        }
    }
}