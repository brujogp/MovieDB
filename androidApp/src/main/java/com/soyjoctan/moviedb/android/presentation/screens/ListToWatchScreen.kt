package com.soyjoctan.moviedb.android.presentation.screens

import android.os.Build
import androidx.compose.material3.AssistChip
import androidx.annotation.RequiresApi
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.node.modifierElementOf
import androidx.compose.ui.unit.dp
import com.soyjoctan.moviedb.android.presentation.commons.ComposableDetailsMovieBottomSheet
import com.soyjoctan.moviedb.android.presentation.commons.ComposableVerticalLazyGrid
import com.soyjoctan.moviedb.android.presentation.commons.Subtitle
import com.soyjoctan.moviedb.android.presentation.models.Routes
import com.soyjoctan.moviedb.android.presentation.viewmodels.MovieViewModel
import com.soyjoctan.moviedb.presentation.models.ClassBaseItemModel
import com.soyjoctan.moviedb.presentation.models.GenreModel
import com.soyjoctan.moviedb.presentation.models.ItemToWatchModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.collections.ArrayList

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
    val genresFilterBottomSheetState: ModalBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
    )
    val detailsBottomSheetState: ModalBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
    )

    Column {
        Subtitle("Películas para ver", null)

        Row(
            Modifier
                .horizontalScroll(scrollState)
                .padding(end = 16.dp)
        ) {
            AddAssistChipFilter("Géneros", Icons.Default.ClearAll) {
                coroutineScope.launch {
                    if (genresFilterBottomSheetState.isVisible) genresFilterBottomSheetState.hide() else genresFilterBottomSheetState.show()
                }
            }
            AddAssistChipFilter("Películas", Icons.Default.Theaters) {

            }
            AddAssistChipFilter("Series", Icons.Default.Tv) {

            }
            AddAssistChipFilter("Actores", Icons.Default.AccountCircle) {

            }
        }

        ComposableVerticalLazyGrid(
            result = convertItemsToWatch(itemsToWatch),
            viewModel = viewModel,
            listState = null,
            bottomSheetState = null,
            onClickMoviePoster = {
                coroutineScope.launch {
                    if (detailsBottomSheetState.isVisible) detailsBottomSheetState.hide() else detailsBottomSheetState.show()
                }
            }
        )
    }

    ComposableDetailsMovieBottomSheet(
        modalState = detailsBottomSheetState,
        viewModel = viewModel,
        onNavigationController = {
            onNavigationController(Routes.CompleteDetailsItemScreen.route)
        }
    )

    GenresFilterBottomSheet(
        genresFilterBottomSheetState,
        itemsToWatch
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun GenresFilterBottomSheet(
    genresFilterBottomSheetState: ModalBottomSheetState,
    itemsToWatch: ArrayList<ItemToWatchModel>?
) {
    ModalBottomSheetLayout(
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        scrimColor = Color(0xC8000000),
        sheetContent = {
            val elements = getGenres(itemsToWatch)

            LazyColumn(
                content = {
                    items(elements) { item: GenreModel ->
                        item.name?.let {
                            ButtonCheckbox(it)
                        }
                    }
                },
                modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {}
        },
        sheetState = genresFilterBottomSheetState
    )
}

fun getGenres(itemsToWatch: ArrayList<ItemToWatchModel>?): ArrayList<GenreModel> {
    val items: ArrayList<GenreModel> = arrayListOf()
    itemsToWatch?.forEach { item ->
        item.genres?.forEach { genre ->
            if (!items.contains(genre))
                items.add(genre)
        }
    }

    return items
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddAssistChipFilter(s: String, icon: ImageVector, onClick: () -> Unit) {
    AssistChip(
        onClick = onClick,
        label = {
            Text(s)
        },
        leadingIcon = {
            Icon(icon, "")
        },
        modifier = Modifier.padding(start = 16.dp)
    )
}

@Composable
fun ButtonCheckbox(s: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 8.dp)
    ) {
        Text(text = s)
        Checkbox(
            checked = false,
            modifier = Modifier.padding(0.dp),
            onCheckedChange = {

            }
        )
    }
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