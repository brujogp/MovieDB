package com.soyjoctan.moviedb.android.presentation.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ClearAll
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType.Companion.Text
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.soyjoctan.moviedb.android.presentation.commons.ComposableDetailsMovieBottomSheet
import com.soyjoctan.moviedb.android.presentation.commons.ComposableNoItemsToShow
import com.soyjoctan.moviedb.android.presentation.commons.ComposableVerticalLazyGrid
import com.soyjoctan.moviedb.android.presentation.commons.Subtitle
import com.soyjoctan.moviedb.android.presentation.models.Routes
import com.soyjoctan.moviedb.android.presentation.viewmodels.MovieViewModel
import com.soyjoctan.moviedb.presentation.models.GenreModel
import com.soyjoctan.moviedb.presentation.models.ItemLikedModel
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
    var genresSelected: ArrayList<GenreModel> by rememberSaveable {
        mutableStateOf(ArrayList())
    }

    makeLikedItemsRequest(viewModel, genresByFilter = genresSelected)
    val coroutineScope: CoroutineScope = rememberCoroutineScope()
    val likedItems: java.util.ArrayList<ItemLikedModel>? by viewModel.likedItemsListMutableLiveData.observeAsState()
    var existGenresBottomSheet: Boolean by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()
    val genresFilterBottomSheetState: ModalBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
    )

    val bottomSheetState: ModalBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
    )

    Column {
        Subtitle("Películas favoritas", null)

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

        if (likedItems != null) {
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
        } else {
            ComposableNoItemsToShow("Aún no hay películas favoritas")
        }
    }

    ComposableDetailsMovieBottomSheet(
        modalState = bottomSheetState,
        viewModel = viewModel,
        onNavigationController = {
            onNavigationController(Routes.CompleteDetailsItemScreen.route)
        }
    )

    if (existGenresBottomSheet) {
        GenresFilterBottomSheet(
            genresFilterBottomSheetState = genresFilterBottomSheetState,
            itemsToWatch = convertItemsToCommonPresentationLisItems(likedItems),
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

private fun makeLikedItemsRequest(
    viewModel: MovieViewModel,
    genresByFilter: ArrayList<GenreModel>
) {
    viewModel.getLikedItems(genresByFilter)
}
