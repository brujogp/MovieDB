package com.soyjoctan.moviedb.android.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.soyjoctan.moviedb.android.presentation.commons.ComposableCardPoster
import com.soyjoctan.moviedb.android.presentation.commons.ComposableDetailsMovieBottomSheet
import com.soyjoctan.moviedb.android.presentation.models.CarouselModel
import com.soyjoctan.moviedb.android.presentation.viewmodels.MovieViewModel
import com.soyjoctan.moviedb.presentation.models.FindByGenreModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.ArrayList

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ListMovieByGenreScreen(viewModel: MovieViewModel, genreName: String?) {
    val scaffoldState = rememberScaffoldState()

    val coroutineScope: CoroutineScope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
    )

    val result: ArrayList<FindByGenreModel>? by viewModel.moviesByGenreModelMutableLiveDataObservable.observeAsState()

    Scaffold(
        scaffoldState = scaffoldState,
        drawerContent = {
            Text("Hola mundo")
            Divider()
            Text("Hola mundo")
            Divider()
        },
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Películas de ${genreName?.lowercase()}")
                },
                navigationIcon = {
                    IconButton(onClick = {
                        coroutineScope.launch {
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
            Box(
                modifier = Modifier
                    .padding(it)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(
                        top = 16.dp,
                        bottom = 32.dp,
                        start = 0.dp,
                        end = 0.dp
                    ),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    content = {
                        result?.let { list ->
                            items(list.toList()) { item: FindByGenreModel ->
                                ComposableCardPoster(
                                    CarouselModel(
                                        movieName = item.movieName,
                                        posterPathImage = item.posterPathImage,
                                        popularity = item.popularity,
                                        movieId = item.movieId,
                                        backdropPath = item.backdropPath
                                    )
                                ) {
                                    coroutineScope.launch {
                                        bottomSheetState.show()
                                    }
                                    viewModel.movieDetailsSelected.value = it
                                }
                            }
                        }
                    },
                    modifier = Modifier.padding(start = 12.dp, end = 12.dp)
                )
            }
        })

    ComposableDetailsMovieBottomSheet(
        modalState = bottomSheetState,
        scope = coroutineScope,
        viewModel = viewModel
    )
}