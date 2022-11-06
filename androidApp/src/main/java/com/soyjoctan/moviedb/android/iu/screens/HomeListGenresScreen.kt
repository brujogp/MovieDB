package com.soyjoctan.moviedb.android.iu.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.soyjoctan.moviedb.android.data.MovieViewModel
import com.soyjoctan.moviedb.android.iu.commons.TextItem
import com.soyjoctan.moviedb.model.Genre


@Composable
fun HomeGenres(
    viewModel: MovieViewModel,
    genres: List<Genre>?,
    onClickGenre: (genre: Genre) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (genres.isNullOrEmpty()) {
            Button({
                viewModel.getGenres()
            }) {
                Text("Cargar datos")
            }
        }

        genres?.let {
            ViewListGenres(
                genres = it,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                onClickGenre = onClickGenre
            )
        }
    }
}

@Composable
fun ViewListGenres(genres: List<Genre>, modifier: Modifier, onClickGenre: (genre: Genre) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        items(genres) { item ->
            TextItem(gender = item, modifier = modifier, onClickGenre = onClickGenre)
            Spacer(
                modifier = Modifier
                    .background(Color.Gray)
                    .fillMaxWidth()
                    .height(1.dp)
            )
        }
    }
}
