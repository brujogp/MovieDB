package com.soyjoctan.moviedb.android.presentation.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import com.soyjoctan.moviedb.android.presentation.commons.ComposableDetailsMovieBottomSheet
import com.soyjoctan.moviedb.android.presentation.commons.ComposableVerticalLazyGrid
import com.soyjoctan.moviedb.android.presentation.commons.Subtitle
import com.soyjoctan.moviedb.android.presentation.models.Routes
import com.soyjoctan.moviedb.android.presentation.viewmodels.MovieViewModel
import com.soyjoctan.moviedb.presentation.models.PresentationModelParent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ListFavoritesMoviesScreen(
    viewModel: MovieViewModel,
    onNavigationController: (path: String) -> Unit
) {
    makeLikedItemsRequest(viewModel)

    val coroutineScope: CoroutineScope = rememberCoroutineScope()
    val likedItems by viewModel.likedItemsListMutableLiveData.observeAsState()

    val bottomSheetState: ModalBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
    )

    Column {
        Subtitle("Películas favoritas", null)
        ComposableVerticalLazyGrid(
            result = convertItemsToClassBaseItemModel(likedItems as ArrayList<PresentationModelParent>),
            viewModel = viewModel,
            listState = null,
            bottomSheetState = null,
            onClickMoviePoster = {
                coroutineScope.launch {
                    bottomSheetState.show()
                }
            }
        )
    }

    ComposableDetailsMovieBottomSheet(
        modalState = bottomSheetState,
        viewModel = viewModel,
        onNavigationController = {
            onNavigationController(Routes.CompleteDetailsItemScreen.route)
        }
    )
}

fun makeLikedItemsRequest(viewModel: MovieViewModel) {
    viewModel.getLikedItems()
}
