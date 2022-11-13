package com.soyjoctan.moviedb.android.presentation.commons

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
import com.soyjoctan.moviedb.android.presentation.models.CarouselModel
import com.soyjoctan.moviedb.android.presentation.viewmodels.MovieViewModel
import com.soyjoctan.moviedb.presentation.models.DetailsMovieModel
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ComposableDetailsMovieBottomSheet(
    modalState: ModalBottomSheetState,
    scope: CoroutineScope,
    viewModel: MovieViewModel,
    onNavigationController: (path: String) -> Unit
) {
    val movieSelected: CarouselModel? by viewModel.itemDetailsSelected.observeAsState()
    val detailMovieSelected: DetailsMovieModel? by viewModel.detailsMovieLiveDataObservable.observeAsState()

    var isLoading by rememberSaveable { mutableStateOf(true) }

    var isLiked by rememberSaveable { mutableStateOf(false) }
    var isInWishList by rememberSaveable { mutableStateOf(false) }

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

                    /*
                    LandscapeImage(stringPath = movieSelected?.backdropPath)
                    ConstraintLayout(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                    ) {
                        val (iconBookmark, text, iconLike) = createRefs()

                        IconButton(
                            onClick = {
                                isLiked = !isLiked
                            },
                            modifier = Modifier.constrainAs(iconBookmark) {
                                top.linkTo(iconLike.bottom)
                                end.linkTo(parent.end, margin = 8.dp)
                            }
                        ) {
                            Icon(
                                if (isLiked) Icons.Filled.ThumbUp else Icons.Outlined.ThumbUp,
                                contentDescription = "Me gusta",
                                tint = Color.White
                            )
                        }

                        IconButton(
                            onClick = { isInWishList = !isInWishList },
                            modifier = Modifier.constrainAs(iconLike) {
                                end.linkTo(parent.end, margin = 8.dp)
                            }
                        ) {
                            Icon(
                                if (isInWishList) Icons.Filled.Check else Icons.Outlined.Add,
                                contentDescription = "Añadir a la wish list",
                                tint = Color.White
                            )
                        }

                        Text(
                            text = movieSelected?.itemName ?: "",
                            color = Color.White,
                            modifier = Modifier.constrainAs(text) {
                                bottom.linkTo(parent.bottom, margin = 16.dp)
                                start.linkTo(parent.start, margin = 16.dp)
                            },
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 32.sp,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    */

                    ComposableLandscapeBackdropMovie(movieSelected)
                }
                if (detailMovieSelected != null) {
                    isLoading = false
                    Text(
                        text = detailMovieSelected!!.overview!!,
                        modifier = Modifier.padding(16.dp),
                        lineHeight = 26.sp
                    )
                    Button(
                        onClick = {
                            onNavigationController(CompleteDetailsItemScreen.route)
                            viewModel.detailsOfItemSelected.value = detailMovieSelected
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
        scrimColor = Color(0xC8000000)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {}
    }
}

fun makeDetailRequest(viewModel: MovieViewModel, movieId: Long) {
    viewModel.getMovieDetailsById(movieId)
}
