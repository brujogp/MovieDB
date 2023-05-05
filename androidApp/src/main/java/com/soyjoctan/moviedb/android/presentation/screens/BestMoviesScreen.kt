package com.soyjoctan.moviedb.android.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MovieFilter
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.soyjoctan.moviedb.android.presentation.commons.ComposableDetailsMovieBottomSheet
import com.soyjoctan.moviedb.android.presentation.commons.ComposableMainScaffold
import com.soyjoctan.moviedb.android.presentation.commons.ComposableVerticalLazyGrid
import com.soyjoctan.moviedb.android.presentation.extensions.OnBottomReached
import com.soyjoctan.moviedb.android.presentation.models.Routes
import com.soyjoctan.moviedb.android.presentation.viewmodels.MovieViewModel
import com.soyjoctan.moviedb.presentation.models.ClassBaseItemModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BestMoviesScreen(
    viewModel: MovieViewModel,
    onNavigationController: (path: String) -> Unit
) {
    val result: ArrayList<ClassBaseItemModel>? by viewModel.listTopRatedModelMoviesObservable.observeAsState()

    var currentPage by rememberSaveable { mutableStateOf(1L) }

    val scaffoldState = rememberScaffoldState()
    val bottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
    )
    val coroutineScope = rememberCoroutineScope()
    var isLoading by rememberSaveable { mutableStateOf(false) }
    val listState = rememberLazyGridState()

    ComposableMainScaffold(
        scaffoldState = scaffoldState,
        onNavigationController = onNavigationController,
        content = {
            Box(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                isLoading = if (result != null) {
                    ComposableVerticalLazyGrid(
                        result!!,
                        viewModel,
                        listState,
                        bottomSheetState,
                        null
                    )
                    false
                } else {
                    true
                }
            }
            if (isLoading) {
                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 2.dp)
                )
            }
        },
        titleSection = "Mejores películas",
        coroutineScope = coroutineScope,
        onFloatingButtonClick = {},
        drawableOnClick = { icon ->
            when (icon) {
                Icons.Filled.Home -> {
                    onNavigationController(Routes.HomeScreen.route)
                }

                Icons.Filled.MovieFilter -> {
                    onNavigationController(Routes.ListToWatchScreen.route)
                }

                Icons.Filled.ThumbUp -> {
                    onNavigationController(Routes.ListFavoritesMoviesScreen.route)
                }
            }
        }

    )

    listState.OnBottomReached {
        currentPage += 1
        viewModel.getTopRatedMovies(currentPage, result)
    }

    ComposableDetailsMovieBottomSheet(
        modalState = bottomSheetState,
        viewModel = viewModel,
        onNavigationController
    )
}
