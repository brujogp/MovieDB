package com.soyjoctan.moviedb.android.presentation.commons

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.soyjoctan.moviedb.android.domain.models.TopRated
import java.util.ArrayList
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Compost
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Start

import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
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
            items(it.toList()) { item: TopRated ->
                CardCarouselItem(item)
            }
        }
    }
}

@Composable
fun CardCarouselItem(item: TopRated) {
    Column(
        modifier = Modifier.width(170.dp),
    ) {
        Card(
            modifier = Modifier
                .height(250.dp)
                .width(170.dp),
            elevation = 8.dp,
            backgroundColor = Color.Gray,
        ) {
            Box(contentAlignment = Alignment.BottomStart) {
                PortalImage(item.posterPathImage)
                ComposableMovieRate(item)
            }
        }

        Text(
            text = item.movieName,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Composable
fun ComposableMovieRate(item: TopRated) {
    Card(
        modifier = Modifier
            .padding(8.dp),
        shape = RoundedCornerShape(30.dp)
    ) {
        Row(
            modifier = Modifier
                .background(MaterialTheme.colors.secondary)
                .padding(top = 4.dp, bottom = 4.dp, start = 8.dp, end = 8.dp)
        ) {
            Icon(
                Icons.Filled.Star,
                contentDescription = "",
                tint = Color.White,
                modifier = Modifier.size(18.dp)
            )
            Text(
                textAlign = TextAlign.Center,
                text = String.format("%.1f", item.popularity),
                color = Color.White, fontSize = 12.sp,
                modifier = Modifier.padding(start = 4.dp)
            )

        }
    }
}
