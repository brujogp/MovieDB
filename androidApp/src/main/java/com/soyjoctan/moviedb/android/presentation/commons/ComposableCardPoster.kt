package com.soyjoctan.moviedb.android.presentation.commons

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.node.modifierElementOf
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.soyjoctan.moviedb.android.presentation.models.CarouselModel

@Composable
fun ComposableCardPoster(item: CarouselModel) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxHeight()
                .width(180.dp),
            elevation = 8.dp,
            backgroundColor = Color.Transparent,
        ) {
            Column {
                Box(contentAlignment = Alignment.BottomStart) {
                    PortalImage(item.posterPathImage)

                    if (item.popularity != null)
                        ComposableMovieRate(item)
                }
                Text(
                    text = item.movieName ?: "",
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .width(150.dp),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

    }
}

