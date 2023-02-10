package com.soyjoctan.moviedb.android.presentation.commons

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import com.soyjoctan.moviedb.android.presentation.models.Routes.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.soyjoctan.moviedb.android.presentation.viewmodels.MovieViewModel
import com.soyjoctan.moviedb.presentation.models.ClassBaseItemModel
import com.soyjoctan.moviedb.presentation.models.DetailsMovieModel
import com.soyjoctan.moviedb.presentation.models.ItemLikedModel
import com.soyjoctan.moviedb.presentation.models.ItemToWatchModel
import kotlinx.coroutines.launch
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ComposableDetailsMovieBottomSheet(
    modalState: ModalBottomSheetState,
    viewModel: MovieViewModel,
    onNavigationController: (path: String) -> Unit
) {
    val movieSelected: ClassBaseItemModel? by viewModel.itemDetailsSelected.observeAsState()
    val itemToWatchFromDb by viewModel.searchItemToWatchByIdListLiveDataObservable.observeAsState()
    val likedItemFromDb by viewModel.searchLikedItemsToWatchByIdListLiveDataObservable.observeAsState()
    val detailMovieSelected: DetailsMovieModel? by viewModel.detailsMovieLiveDataObservable.observeAsState()

    var isLoading by rememberSaveable { mutableStateOf(true) }
    val scope = rememberCoroutineScope()

    movieSelected?.itemId?.let {
        makeDetailRequest(viewModel, it)
    }

    ModalBottomSheetLayout(
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetContent = {
            Column(modifier = Modifier.fillMaxWidth()) {
                Box(
                    Modifier
                        .fillMaxWidth(),
                    contentAlignment = Alignment.TopCenter
                ) {
                    ComposableLandscapeBackdropMovie(
                        movieSelected = movieSelected,
                        wasMarkedToWatch = itemToWatchFromDb?.itemId == movieSelected?.itemId,
                        wasMarkedAsLikedItem = likedItemFromDb?.itemId == movieSelected?.itemId,
                        onClickToLikeButton = {
                            addItemToLikeList(it, viewModel, detailMovieSelected, movieSelected)
                        },
                        onClickToWatchButton = {
                            addItemToWatchList(it, viewModel, detailMovieSelected, movieSelected)
                        }
                    )
                }

                if (likedItemFromDb != null) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp)
                    ) {
                        RatingBar(
                            modifier = Modifier.fillMaxWidth(),
                            rating = likedItemFromDb!!.rating!!
                        ) {
                            viewModel.updateRatingForLikedItem(it, likedItemFromDb!!.itemId)
                        }
                    }
                }

                if (detailMovieSelected != null) {
                    isLoading = false

                    ComposableDescriptionTextMovie(
                        descriptionMovie = detailMovieSelected!!.overview!!,
                        modifier = Modifier,
                        maxLine = 3
                    )
                    Button(
                        onClick = {
                            onNavigationController(CompleteDetailsItemScreen.route)
                            viewModel.detailsOfItemSelected.value = detailMovieSelected

                            scope.launch {
                                if (modalState.isVisible) {
                                    modalState.hide()
                                }
                            }
                        },
                        modifier = Modifier
                            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp, top = 16.dp)
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = "Saber más",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = Color.White,
                            modifier = Modifier.height(32.dp)
                        )
                    }
                } else {
                    LinearProgressIndicator(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 2.dp)
                    )
                }
            }
        },
        sheetState = modalState,
        scrimColor = Color(0xC8000000),
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {}
        }
    )
}

@RequiresApi(Build.VERSION_CODES.O)
fun addItemToLikeList(
    b: Boolean,
    viewModel: MovieViewModel,
    detailsMovieModel: DetailsMovieModel?,
    movieSelected: ClassBaseItemModel?
) {
    if (b) {
        viewModel.addItemToLikedList(
            detailsMovieModel.let {
                ItemLikedModel(
                    itemId = it?.itemId,
                    itemName = it?.itemName,
                    posterPathImage = it?.posterPathImage,
                    popularity = it?.popularity,
                    backdropPath = it?.backdropPath,
                    genres = it?.genres,
                    dateAdded = LocalDate.now().toString(),
                    fromListToWatch = false
                )
            }
        )
        makeDetailRequest(viewModel, movieSelected?.itemId!!)
        viewModel.getLikedItems()
    } else {
        viewModel.updateRatingForLikedItem(detailsMovieModel?.itemId!!)
        viewModel.searchLikedItemById(detailsMovieModel.itemId!!)
        viewModel.getLikedItems()
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun addItemToWatchList(
    b: Boolean,
    viewModel: MovieViewModel,
    detailMovieSelected: DetailsMovieModel?,
    movieSelected: ClassBaseItemModel?
) {
    if (b) {
        viewModel.addItemToWatch(
            ItemToWatchModel(
                itemId = detailMovieSelected?.itemId!!,
                itemName = detailMovieSelected.itemName!!,
                whereWatch = "Sin especificar",
                posterPathImage = detailMovieSelected.posterPathImage,
                popularity = detailMovieSelected.popularity,
                backdropPath = detailMovieSelected.backdropPath,
                genres = detailMovieSelected.genres,
                dateAdded = LocalDate.now().toString()
            )
        )
        makeDetailRequest(viewModel, movieSelected?.itemId!!)
        viewModel.getItemsToWatch()
    } else {
        viewModel.removeItemToWatch(detailMovieSelected?.itemId!!)
        viewModel.searchItemToWatchById(detailMovieSelected.itemId!!)
        viewModel.getItemsToWatch()
    }
}

fun makeDetailRequest(viewModel: MovieViewModel, movieId: Long) {
    viewModel.getMovieDetailsById(movieId)
    viewModel.searchItemToWatchById(movieId)
    viewModel.searchLikedItemById(movieId)
}
