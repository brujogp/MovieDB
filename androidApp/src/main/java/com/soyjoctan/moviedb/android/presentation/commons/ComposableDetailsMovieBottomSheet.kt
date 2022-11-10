package com.soyjoctan.moviedb.android.presentation.commons

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment

import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.soyjoctan.moviedb.android.presentation.models.CarouselModel
import com.soyjoctan.moviedb.android.presentation.viewmodels.MovieViewModel
import com.soyjoctan.moviedb.presentation.models.DetailsMovieModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ComposableDetailsMovieBottomSheet(
    modalState: ModalBottomSheetState,
    scope: CoroutineScope,
    viewModel: MovieViewModel
) {
    val movieSelected: CarouselModel? by viewModel.movieDetailsSelected.observeAsState()
    val detailMovieSelected: DetailsMovieModel? by viewModel.detailsMovieMutableLiveDataObservable.observeAsState()

    var isLoading by rememberSaveable { mutableStateOf(true) }

    movieSelected?.movieId?.let {
        makeDetailRequest(viewModel, it)
    }

    ModalBottomSheetLayout(
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetContent = {
            Column(modifier = Modifier.fillMaxWidth()) {
                Box(
                    Modifier
                        .fillMaxWidth(),
                    contentAlignment = Alignment.BottomStart
                ) {
                    LandscapeImage(stringPath = movieSelected?.backdropPath)
                    Text(
                        text = movieSelected?.movieName ?: "",
                        color = Color.White,
                        modifier = Modifier.padding(bottom = 16.dp, end = 32.dp, start = 12.dp),
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 32.sp,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                if (detailMovieSelected != null) {
                    isLoading = false
                    Text(
                        text = detailMovieSelected!!.overview!!,
                        modifier = Modifier.padding(16.dp),
                        lineHeight = 26.sp
                    )
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
