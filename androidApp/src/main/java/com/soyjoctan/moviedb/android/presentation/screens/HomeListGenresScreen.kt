package com.soyjoctan.moviedb.android.presentation.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
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
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.soyjoctan.moviedb.android.presentation.commons.Subtitle
import com.soyjoctan.moviedb.android.presentation.viewmodels.MovieViewModel
import com.soyjoctan.moviedb.android.presentation.commons.TextItem
import com.soyjoctan.moviedb.android.presentation.commons.ViewCarousel
import com.soyjoctan.moviedb.model.genres.Genre
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
    val topRatedMovies by viewModel.listTopRatedMoviesObservable.observeAsState()

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
                Icon(Icons.Filled.Add, "Add item")
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
                Subtitle("Las mejores películas")
                ViewCarousel(topRatedMovies, Modifier)

                Subtitle("Géneros")
                genres?.let { genres ->
                    ViewListGenres(
                        genres = genres,
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        onClickGenre = onClickGenre,
                    )
                }
            }

        }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ViewListGenres(genres: List<Genre>, modifier: Modifier, onClickGenre: (genre: Genre) -> Unit) {
    LazyHorizontalStaggeredGrid(
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp),
        modifier = modifier
            .height(130.dp),
        rows = StaggeredGridCells.Fixed(3),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(genres) { item ->
            TextItem(
                gender = item,
                modifier = modifier,
                onClickGenre = onClickGenre,
            )
        }
    }
}


fun makeRequests(viewModel: MovieViewModel) {
    viewModel.getGenres()
    viewModel.getTopRatedMovies()
}

