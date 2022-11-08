package com.soyjoctan.moviedb.android.presentation.commons

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CustomDivider() {
    Divider(
        modifier = Modifier
            .padding(top = 20.dp, bottom = 0.dp, start = 8.dp, end = 8.dp)
            .fillMaxWidth()
            .height(1.dp)
            .background(MaterialTheme.colors.primary.copy(alpha = 0.1F))
    )
}
