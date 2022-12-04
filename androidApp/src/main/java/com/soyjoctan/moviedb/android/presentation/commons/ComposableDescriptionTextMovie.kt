package com.soyjoctan.moviedb.android.presentation.commons

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ComposableDescriptionTextMovie(descriptionMovie: String, modifier: Modifier) {
    Text(
        descriptionMovie,
        modifier = modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp),
        lineHeight = 26.sp
    )
}
