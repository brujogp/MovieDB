package com.soyjoctan.moviedb.android.presentation.commons

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.soyjoctan.moviedb.android.domain.models.TopRated
import java.util.ArrayList
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ViewCarousel(content: ArrayList<TopRated>?, modifier: Modifier) {
    LazyRow(
        contentPadding = PaddingValues(top = 0.dp, bottom = 8.dp, start = 16.dp, end = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        content?.let {
            items(it.toList()) { item ->
                Card(
                    modifier = Modifier
                        .height(250.dp)
                        .width(170.dp),
                    elevation = 16.dp,
                    backgroundColor = Color.Gray,
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            item.movieName,
                            modifier = Modifier.padding(16.dp),
                            color = Color.White,
                            textAlign = TextAlign.Center,
                            lineHeight = 21.sp
                        )
                    }
                }
            }
        }
    }
}
