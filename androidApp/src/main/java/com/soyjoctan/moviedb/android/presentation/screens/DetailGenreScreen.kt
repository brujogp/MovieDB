package com.soyjoctan.moviedb.android.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import com.soyjoctan.moviedb.android.presentation.viewmodels.MovieViewModel
import com.soyjoctan.moviedb.android.presentation.MainActivity

@Composable
fun MainDetailGenre() {
    val viewModel =
        ViewModelProvider(LocalContext.current as MainActivity)[MovieViewModel::class.java]
    viewModel.genreSelected?.let {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(modifier = Modifier.align(Alignment.Center)) {
                Text(it.name!!)
            }
        }
    }
}
