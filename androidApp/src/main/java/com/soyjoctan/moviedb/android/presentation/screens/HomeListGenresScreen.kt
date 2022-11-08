package com.soyjoctan.moviedb.android.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.soyjoctan.moviedb.android.presentation.commons.*
import com.soyjoctan.moviedb.android.presentation.viewmodels.MovieViewModel
import com.soyjoctan.moviedb.data.model.genres.Genre
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun HomeGenres(
    viewModel: MovieViewModel,
    onClickGenre: (genre: Genre) -> Unit,
) {
    val scaffoldState = rememberScaffoldState()
    val scope: CoroutineScope = rememberCoroutineScope()

    val genres: List<Genre>? by viewModel.listGenresObservable.observeAsState()
    val topRatedMovies by viewModel.listTopRatedModelMoviesObservable.observeAsState()

    makeRequests(viewModel)

    Scaffold(
        scaffoldState = scaffoldState,
        drawerContent = {
            Text("Hola mundo")
            Divider()
            Text("Hola mundo")
            Divider()
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Filled.Add, "Add item", tint = Color.White)
            }
        },
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Géneros")
                },
                navigationIcon = {
                    IconButton(onClick = {
                        scope.launch {
                            scaffoldState.drawerState.apply {
                                open()
                            }
                        }
                    }) {
                        Icon(Icons.Filled.Menu, "Menu button")
                    }
                },
                contentColor = Color.White,
                elevation = 16.dp
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .padding(it)
            ) {

                Subtitle("Géneros")
                genres?.let { genres ->
                    ComposableStaggered(
                        genres = genres,
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        onClickGenre = onClickGenre,
                    )
                }

                CustomDivider()

                Subtitle("Las mejores películas")
                ViewCarousel(topRatedMovies, Modifier)
            }

        }
    )
}


fun makeRequests(viewModel: MovieViewModel) {
    viewModel.getGenres()
    viewModel.getTopRatedMovies()
}

