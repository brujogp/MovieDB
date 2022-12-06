package com.soyjoctan.moviedb.android.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MovieFilter
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
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
import kotlinx.coroutines.CoroutineScope
import java.util.ArrayList

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ListMovieByGenreScreen(
    viewModel: MovieViewModel,
    genreName: String?,
    genreId: Long?,
    onNavigationController: (path: String) -> Unit
) {
    var currentPage by rememberSaveable { mutableStateOf(1L) }
    var isLoading by rememberSaveable { mutableStateOf(true) }

    val scaffoldState = rememberScaffoldState()

    val coroutineScope: CoroutineScope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
    )

    val listState = rememberLazyGridState()
    val result: ArrayList<ClassBaseItemModel>? by viewModel.moviesByGenreModelMutableLiveDataObservable.observeAsState()

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
        titleSection = genreName,
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
            }
        }
    )

    listState.OnBottomReached {
        // do on load more
        currentPage += 1
        viewModel.getMoviesByGenre(genreId!!, currentPage, result)
    }

    ComposableDetailsMovieBottomSheet(
        modalState = bottomSheetState,
        viewModel = viewModel,
        onNavigationController
    )
}
