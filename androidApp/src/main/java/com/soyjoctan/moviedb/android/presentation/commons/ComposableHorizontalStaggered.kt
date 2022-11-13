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
fun ComposableStaggered(genres: List<GenreModel>, modifier: Modifier, onClickGenre: (genre: GenreModel) -> Unit) {
    LazyHorizontalStaggeredGrid(
        contentPadding = PaddingValues(start = 16.dp, end = 0.dp),
        modifier = modifier
            .height(90.dp),
        rows = StaggeredGridCells.Fixed(2),
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

