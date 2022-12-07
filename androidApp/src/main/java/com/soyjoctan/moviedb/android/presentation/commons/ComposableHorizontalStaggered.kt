package com.soyjoctan.moviedb.android.presentation.commons

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.soyjoctan.moviedb.presentation.models.GenreModel


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ComposableStaggered(
    genres: List<GenreModel>,
    modifier: Modifier,
    onClickGenre: (genre: GenreModel) -> Unit,
    cellsRow: Int = 2
) {
    val height = if (cellsRow == 2) 90.dp else 45.dp

    LazyHorizontalStaggeredGrid(
        contentPadding = PaddingValues(start = 0.dp, end = 16.dp),
        modifier = modifier
            .height(height),
        rows = StaggeredGridCells.Fixed(cellsRow),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(genres) { item ->
            TextItem(
                gender = item,
                modifier = modifier,
                onClickGenre = onClickGenre
            )
        }
    }
}

