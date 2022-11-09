package com.soyjoctan.moviedb.android.presentation.commons

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage


@Composable
fun LandscapeImage(stringPath: String?, contentDescription: String = "") {
    AsyncImage(
        modifier = Modifier
            .height(200.dp)
            .fillMaxWidth()
            .background(Color.Black),
        model = BasePaths.BASE_PATH_IMAGE + stringPath,
        contentDescription = contentDescription,
        contentScale = ContentScale.Crop,
alpha = .5f
    )
}
