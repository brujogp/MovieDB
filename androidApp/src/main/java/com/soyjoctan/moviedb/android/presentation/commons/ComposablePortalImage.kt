package com.soyjoctan.moviedb.android.presentation.commons

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

object BasePaths {
    const val BASE_PATH_IMAGE = "https://image.tmdb.org/t/p/w500"
}

@Composable
fun PortalImage(stringPath: String?, height: Dp?) {
    val newModifier = if (height == null) Modifier.height(270.dp)
    else Modifier.height(height)
    AsyncImage(
        modifier = newModifier
            .width(180.dp),
        model = BasePaths.BASE_PATH_IMAGE + stringPath,
        contentDescription = "",
        contentScale = ContentScale.Crop,
    )
}