package com.soyjoctan.moviedb.android.presentation.commons

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.soyjoctan.moviedb.android.presentation.models.CarouselModel
import com.soyjoctan.moviedb.android.presentation.viewmodels.MovieViewModel
import com.soyjoctan.moviedb.presentation.models.FindByGenreModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.ArrayList

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ComposableVerticalLazyGrid(
    result: ArrayList<FindByGenreModel>,
    viewModel: MovieViewModel,
    coroutineScope: CoroutineScope,
    listState: LazyGridState?,
    bottomSheetState: ModalBottomSheetState
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(
            top = 16.dp,
            bottom = 32.dp,
            start = 0.dp,
            end = 0.dp
        ),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        content = {
            items(result!!.toList()) { item: FindByGenreModel ->
                ComposableCardPoster(
                    CarouselModel(
                        itemName = item.itemName,
                        posterPathImage = item.posterPathImage,
                        popularity = item.popularity,
                        itemId = item.itemId,
                        backdropPath = item.backdropPath
                    )
                ) {
                    coroutineScope.launch {
                        bottomSheetState.show()
                    }
                    viewModel.itemDetailsSelected.value = it
                }
            }
        },
        modifier = Modifier
            .padding(start = 12.dp, end = 12.dp),
        state = listState ?: rememberLazyGridState()
    )
}