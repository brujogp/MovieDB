package com.soyjoctan.moviedb.android.presentation.commons

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.soyjoctan.moviedb.android.presentation.models.CarouselModel
import com.soyjoctan.moviedb.android.presentation.viewmodels.MovieViewModel
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
                        movieSelected?.movieName ?: "",
                        modifier = Modifier.padding(bottom= 16.dp, end = 32.dp, start = 12.dp),
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 32.sp,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }


            }
        },
        sheetState = modalState,
        scrimColor = Color(0x7C3A3A3A)

    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
        }
        /*
        Box(
            Modifier
                .fillMaxWidth()
                .padding(64.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            LandscapeImage(stringPath = movieSelected?.backdropPath)
            Text(movieSelected?.movieName ?: "")
        }
        */
    }
}