package com.soyjoctan.moviedb.android.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentComposer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.unit.dp
import com.soyjoctan.moviedb.android.presentation.commons.ComposableDetailsMovieBottomSheet
import com.soyjoctan.moviedb.android.presentation.commons.ComposableVerticalLazyGrid
import com.soyjoctan.moviedb.android.presentation.commons.Subtitle
import com.soyjoctan.moviedb.android.presentation.models.Routes
import com.soyjoctan.moviedb.android.presentation.viewmodels.MovieViewModel
import com.soyjoctan.moviedb.presentation.models.ClassBaseItemModel
import com.soyjoctan.moviedb.presentation.models.ItemToWatchModel
import com.soyjoctan.moviedb.shared.cache.ItemsToWatch
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.ArrayList

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ListToWatchScreen(
    viewModel: MovieViewModel,
    onNavigationController: (path: String) -> Unit
) {
    viewModel.getItemsToWatch()
    val itemsToWatch: ArrayList<ItemToWatchModel>? by viewModel.itemsToWatchListLiveDataObservable.observeAsState()

    val coroutineScope: CoroutineScope = rememberCoroutineScope()
    val bottomSheetState: ModalBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
    )

    Column {
        Subtitle("Películas para ver", null)

        ComposableVerticalLazyGrid(
            result = convertItemsToWatch(itemsToWatch),
            viewModel = viewModel,
            listState = null,
            bottomSheetState = null,
            onClickMoviePoster = {
                coroutineScope.launch {
                    if (bottomSheetState.isVisible) bottomSheetState.hide() else bottomSheetState.show()
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

fun convertItemsToWatch(itemsToWatch: ArrayList<ItemToWatchModel>?): ArrayList<ClassBaseItemModel> {
    val items: ArrayList<ClassBaseItemModel> = arrayListOf()

    itemsToWatch?.forEach {
        items.add(
            ClassBaseItemModel(
                itemName = it.itemName,
                posterPathImage = it.posterPathImage,
                popularity = it.popularity?.toDouble(),
                itemId = it.itemId,
                backdropPath = it.backdropPath
            )
        )
    }

    return items
}