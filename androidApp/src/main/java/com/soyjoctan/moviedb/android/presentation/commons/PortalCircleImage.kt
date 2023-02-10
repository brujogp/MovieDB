package com.soyjoctan.moviedb.android.presentation.commons

import android.content.res.Resources
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun PortalCircleImage(stringPath: String?, onClick: () -> Unit) {
    AsyncImage(
        modifier = Modifier
            .size(140.dp)
            .clip(CircleShape)
            .clickable { onClick.invoke() }
            .border(2.dp, MaterialTheme.colors.primary, CircleShape),
        model = BasePaths.BASE_PATH_IMAGE + stringPath,
        contentDescription = "",
        contentScale = ContentScale.Crop,
    )
}
