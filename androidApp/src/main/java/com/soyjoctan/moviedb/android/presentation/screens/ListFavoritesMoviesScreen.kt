package com.soyjoctan.moviedb.android.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import com.soyjoctan.moviedb.android.presentation.commons.ComposableDetailsMovieBottomSheet
import com.soyjoctan.moviedb.android.presentation.commons.ComposableVerticalLazyGrid
import com.soyjoctan.moviedb.android.presentation.commons.Subtitle
import com.soyjoctan.moviedb.android.presentation.models.Routes
import com.soyjoctan.moviedb.android.presentation.viewmodels.MovieViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ListFavoritesMoviesScreen(
    viewModel: MovieViewModel,
    onNavigationController: (path: String) -> Unit
) {
    val coroutineScope: CoroutineScope = rememberCoroutineScope()
    val bottomSheetState: ModalBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
    )

    Column {
        Subtitle("Películas favoritas", null)
    }

    ComposableDetailsMovieBottomSheet(
        modalState = bottomSheetState,
        viewModel = viewModel,
        onNavigationController = {
            onNavigationController(Routes.CompleteDetailsItemScreen.route)
        }
    )
}