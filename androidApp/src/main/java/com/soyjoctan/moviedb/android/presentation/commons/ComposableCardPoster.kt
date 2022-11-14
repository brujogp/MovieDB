package com.soyjoctan.moviedb.android.presentation.commons

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.soyjoctan.moviedb.presentation.models.ClassBaseItemModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ComposableCardPoster(item: ClassBaseItemModel, onClickPosterImage: (item: ClassBaseItemModel) -> Unit) {
    Box(
        modifier = Modifier.fillMaxWidth().background(Color.Transparent),
        contentAlignment = Alignment.Center,
    ) {
        Card(
            modifier = Modifier
                .fillMaxHeight()
                .width(180.dp),
            elevation = 0.dp,
            backgroundColor = Color.Transparent,
            onClick = {
                onClickPosterImage(item)
            }
        ) {
            Column {
                Box(contentAlignment = Alignment.BottomStart) {
                    PortalImage(item.posterPathImage)

                    if (item.popularity != null)
                        ComposableMovieRate(item)
                }
                Text(
                    text = item.itemName ?: "",
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

@Preview
@Composable
fun preview() {
    ComposableCardPoster(
        item = ClassBaseItemModel(
            null,
            null,
            null,
            null,
            null
        )
    ) {
    }
}

