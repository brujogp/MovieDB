package com.soyjoctan.moviedb.android.presentation.screens

import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.soyjoctan.moviedb.android.presentation.commons.ComposableLandscapeBackdropMovie
import com.soyjoctan.moviedb.android.presentation.commons.ComposableMainScaffold
import com.soyjoctan.moviedb.android.presentation.commons.LandscapeImage
import com.soyjoctan.moviedb.android.presentation.models.Routes
import com.soyjoctan.moviedb.android.presentation.viewmodels.MovieViewModel
import com.soyjoctan.moviedb.presentation.models.ClassBaseItemModel
import com.soyjoctan.moviedb.presentation.models.DetailsMovieModel
import com.soyjoctan.moviedb.shared.cache.ItemsToWatch
import kotlinx.coroutines.CoroutineScope

@Composable
fun CompleteDetailsItemScreen(
    viewModel: MovieViewModel = viewModel(),
    onNavigationController: (path: String) -> Unit
) {
    val scaffoldState = rememberScaffoldState()
    val scope: CoroutineScope = rememberCoroutineScope()

    val itemSelected: DetailsMovieModel? by viewModel.detailsOfItemSelected.observeAsState()
    val movieSelected: ClassBaseItemModel? by viewModel.itemDetailsSelected.observeAsState()
    val itemToWatchFromDb: ItemsToWatch? by viewModel.searchItemToWatchByIdListLiveDataObservable.observeAsState()

    movieSelected?.itemId?.let {
        viewModel.searchItemToWatchById(it)
    }

    ComposableMainScaffold(
        scaffoldState = scaffoldState,
        titleSection = "Detalle",
        coroutineScope = scope,
        content = {
            itemSelected?.let { itemSelected ->
                Text(text = itemSelected.itemName ?: "")
                ComposableLandscapeBackdropMovie(
                    movieSelected = movieSelected,
                    onClickToWatchButton = {

                    },
                    wasMarkedToWatch = itemToWatchFromDb?.itemId == movieSelected?.itemId
                )
            }
        },
        onFloatingButtonClick = {

        },
        requireTopBar = false,
        drawableOnClick = {
            onNavigationController(Routes.ListToWatchScreen.route)
        },
        onNavigationController = onNavigationController
    )
}
