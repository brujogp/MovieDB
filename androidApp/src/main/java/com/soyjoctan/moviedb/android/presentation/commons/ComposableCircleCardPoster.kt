package com.soyjoctan.moviedb.android.presentation.commons

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.soyjoctan.moviedb.presentation.models.ClassBaseItemModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ComposableCircleCardPoster(
    item: ClassBaseItemModel,
    onClickPosterImage: (item: ClassBaseItemModel) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent),
        contentAlignment = Alignment.Center,
    ) {

        Card(
            elevation = 0.dp,
            backgroundColor = Color.Transparent,
        ) {
            Column {
                Box(contentAlignment = Alignment.BottomStart) {
                    PortalCircleImage(
                        item.posterPathImage
                    ) {
                        onClickPosterImage(item)
                    }

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
