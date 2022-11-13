package com.soyjoctan.moviedb.android.presentation.screens

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import com.soyjoctan.moviedb.android.presentation.viewmodels.MovieViewModel

@Composable
fun ListToWatchScreen(viewModel: MovieViewModel) {
    viewModel.getItemsToWatch()

    val itemsToWatch by viewModel.itemsToWatchListLiveDataObservable.observeAsState()

    Text(text = itemsToWatch?.size.toString())
}