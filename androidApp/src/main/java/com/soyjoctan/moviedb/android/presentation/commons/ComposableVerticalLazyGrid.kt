package com.soyjoctan.moviedb.android.presentation.commons

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.soyjoctan.moviedb.android.presentation.viewmodels.MovieViewModel
import com.soyjoctan.moviedb.presentation.models.ClassBaseItemModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.ArrayList

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ComposableVerticalLazyGrid(
    result: ArrayList<ClassBaseItemModel>,
    viewModel: MovieViewModel,
    listState: LazyGridState?,
    bottomSheetState: ModalBottomSheetState?,
    onClickMoviePoster: (() -> Unit)?
) {
    val coroutineScope: CoroutineScope = rememberCoroutineScope()

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
            items(result.toList()) { item: ClassBaseItemModel ->
               key(item.itemId) {
                   ComposableCardPoster(
                       item = item,
                       onClickPosterImage = {
                           bottomSheetState?.let {
                               coroutineScope.launch {
                                   bottomSheetState.show()
                               }
                           }

                           onClickMoviePoster?.invoke()

                           viewModel.itemDetailsSelected.value = it
                       },
                       null
                   )
               }
            }
        },
        modifier = Modifier
            .padding(start = 12.dp, end = 12.dp),
        state = listState ?: rememberLazyGridState()
    )
}