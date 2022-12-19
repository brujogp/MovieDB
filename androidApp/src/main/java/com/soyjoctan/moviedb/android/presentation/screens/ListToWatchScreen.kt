package com.soyjoctan.moviedb.android.presentation.screens

import android.os.Build
import androidx.compose.material3.AssistChip
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    var genresSelected: ArrayList<GenreModel> by rememberSaveable {
        mutableStateOf(ArrayList())
    }
    viewModel.getItemsToWatch(genresSelected)
    val itemsToWatch: ArrayList<ItemToWatchModel>? by viewModel.itemsToWatchListLiveDataObservable.observeAsState()
    val scrollState = rememberScrollState()
    val coroutineScope: CoroutineScope = rememberCoroutineScope()
    val genresFilterBottomSheetState: ModalBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
    )
    val detailsBottomSheetState: ModalBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
    )

    var existGenresBottomSheet: Boolean by remember { mutableStateOf(false) }

    Column {
        Subtitle("Películas para ver", null)

        Row(
            Modifier
                .horizontalScroll(scrollState)
                .padding(end = 16.dp)
        ) {
            AddAssistChipFilter(
                "Géneros",
                Icons.Default.ClearAll,
                onClick =
                {
                    existGenresBottomSheet = true

                    if (genresSelected.size > 0) {
                        genresSelected = arrayListOf()
                        viewModel.getItemsToWatch()
                    } else {
                        coroutineScope.launch {
                            if (genresFilterBottomSheetState.isVisible) genresFilterBottomSheetState.hide() else genresFilterBottomSheetState.show()
                        }
                    }
                },
                trailingIcon = if (genresSelected.size > 0) Icons.Default.Close else null

            )
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

    if (existGenresBottomSheet) {
        GenresFilterBottomSheet(
            genresFilterBottomSheetState = genresFilterBottomSheetState,
            itemsToWatch = itemsToWatch,
            onGenresChecked = { isChecked, genreItem ->
                if (isChecked && !genresSelected.contains(genreItem)) {
                    genresSelected.add(genreItem)
                } else if (!isChecked && genresSelected.contains(genreItem)) {
                    genresSelected.remove(genreItem)
                }
            },
            onApplyGenres = {
                if (genresSelected.size > 0) {
                    viewModel.getItemsToWatch(genresSelected)
                    existGenresBottomSheet = false
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun GenresFilterBottomSheet(
    genresFilterBottomSheetState: ModalBottomSheetState,
    itemsToWatch: ArrayList<ItemToWatchModel>?,
    onGenresChecked: (isChecked: Boolean, genreName: GenreModel) -> Unit,
    onApplyGenres: () -> Unit
) {
    val rememberCoroutineScope = rememberCoroutineScope()
    ModalBottomSheetLayout(
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        scrimColor = Color(0xC8000000),
        sheetContent = {
            val elements: ArrayList<GenreModel> = getGenres(itemsToWatch)
            LazyColumn(
                content = {
                    itemsIndexed(elements) { index, item: GenreModel ->
                        ButtonCheckbox(
                            item = item,
                            onClick = { isChecked: Boolean, genreItem: GenreModel ->
                                onGenresChecked(isChecked, genreItem)
                            })
                    }
                },
                modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)
            )
            Button(
                onClick = {
                    rememberCoroutineScope.launch {
                        genresFilterBottomSheetState.hide()
                    }
                    onApplyGenres.invoke()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
            ) {
                Text(
                    text = "Aplicar",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color.White,
                    modifier = Modifier.padding(top = 4.dp, bottom = 4.dp)
                )
            }
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
fun AddAssistChipFilter(
    s: String,
    icon: ImageVector,
    onClick: () -> Unit,
    trailingIcon: ImageVector? = null
) {
    AssistChip(
        onClick = onClick,
        label = {
            Text(s)
        },
        leadingIcon = {
            Icon(icon, "")
        },
        modifier = Modifier.padding(start = 16.dp),
        trailingIcon = {
            trailingIcon?.let {
                Icon(Icons.Default.Close, "")
            }
        }
    )
}

@Composable
fun ButtonCheckbox(item: GenreModel, onClick: (isChecked: Boolean, genreName: GenreModel) -> Unit) {
    var isChecked: Boolean by remember { mutableStateOf(false) }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 8.dp)
    ) {
        Text(text = item.name!!, modifier = Modifier.clickable {
            isChecked = !isChecked
        })
        Checkbox(
            checked = isChecked,
            modifier = Modifier.padding(0.dp),
            onCheckedChange = {
                isChecked = it
                onClick(it, item)
            },
            colors = CheckboxDefaults.colors(
                checkedColor = MaterialTheme.colors.primary,
                checkmarkColor = MaterialTheme.colors.onBackground
            )
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