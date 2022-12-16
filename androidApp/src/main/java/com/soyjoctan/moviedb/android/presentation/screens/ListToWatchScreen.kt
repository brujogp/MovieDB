package com.soyjoctan.moviedb.android.presentation.screens

import android.os.Build
import androidx.compose.material3.AssistChip
import androidx.annotation.RequiresApi
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ClearAll
import androidx.compose.material.icons.filled.Theaters
import androidx.compose.material.icons.filled.Tv
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.soyjoctan.moviedb.android.presentation.commons.ComposableDetailsMovieBottomSheet
import com.soyjoctan.moviedb.android.presentation.commons.ComposableVerticalLazyGrid
import com.soyjoctan.moviedb.android.presentation.commons.Subtitle
import com.soyjoctan.moviedb.android.presentation.models.Routes
import com.soyjoctan.moviedb.android.presentation.viewmodels.MovieViewModel
import com.soyjoctan.moviedb.presentation.models.ClassBaseItemModel
import com.soyjoctan.moviedb.presentation.models.ItemToWatchModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.ArrayList

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ListToWatchScreen(
    viewModel: MovieViewModel,
    onNavigationController: (path: String) -> Unit
) {
    viewModel.getItemsToWatch()
    val itemsToWatch: ArrayList<ItemToWatchModel>? by viewModel.itemsToWatchListLiveDataObservable.observeAsState()
    val scrollState = rememberScrollState()
    val coroutineScope: CoroutineScope = rememberCoroutineScope()
    val bottomSheetState: ModalBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
    )

    Column {
        Subtitle("Películas para ver", null)

        Row(Modifier.horizontalScroll(scrollState)) {
            AddAssistChipFilter("Géneros", Icons.Default.ClearAll)
            AddAssistChipFilter("Películas", Icons.Default.Theaters)
            AddAssistChipFilter("Series", Icons.Default.Tv)
            AddAssistChipFilter("Actores", Icons.Default.AccountCircle)
        }

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddAssistChipFilter(s: String, icon: ImageVector) {
    AssistChip(
        onClick = {
        },
        label = {
            Text(s)
        },
        leadingIcon = {
            Icon(icon, "")
        },
        modifier = Modifier.padding(start = 16.dp)
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