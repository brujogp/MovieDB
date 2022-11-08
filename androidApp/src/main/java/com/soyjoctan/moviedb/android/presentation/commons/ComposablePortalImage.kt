package com.soyjoctan.moviedb.android.presentation.commons

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage

object BasePaths {
    const val BASE_PATH_IMAGE = "https://image.tmdb.org/t/p/w500"
}

@Composable
fun PortalImage(stringPath: String?, contentDescription: String = "") {
    AsyncImage(
        modifier = Modifier.fillMaxSize(),
        model = BasePaths.BASE_PATH_IMAGE + stringPath,
        contentDescription = contentDescription,
        contentScale = ContentScale.Crop
    )
}