package com.soyjoctan.moviedb.android.presentation.commons

import androidx.compose.foundation.background
import androidx.compose.runtime.key

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import java.util.ArrayList
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayCircleFilled
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.Alignment

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.soyjoctan.moviedb.presentation.models.ClassBaseItemModel

lateinit var visibleList: ArrayList<ClassBaseItemModel>

@Composable
fun ViewCarousel(
    content: ArrayList<ClassBaseItemModel>?,
    modifier: Modifier,
    onClickPosterImage: (item: ClassBaseItemModel) -> Unit,
    height: Dp?,
    onShowMore: (() -> Unit?)?
) {
    content?.let {
        if (content.size > 10) {
            visibleList = content.take(10) as ArrayList<ClassBaseItemModel>
            visibleList.add(
                ClassBaseItemModel(KEYS.K, KEYS.K, 0.0, 0L, KEYS.K)
            )
        } else {
            visibleList = content
        }

    }

    LazyRow(
        contentPadding = PaddingValues(top = 0.dp, bottom = 8.dp, start = 16.dp, end = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(visibleList.toList()) { item: ClassBaseItemModel ->
            key(item.itemId) {
                if (item.itemName == KEYS.K) {
                    Box(
                        modifier = Modifier.height(250.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Ver lista completa",
                            color = MaterialTheme.colors.onBackground
                        )
                        IconButton(
                            onClick = { onShowMore?.invoke() },
                            Modifier.padding(top = 70.dp)
                        ) {
                            Icon(
                                Icons.Default.PlayCircleFilled,
                                contentDescription = "",
                                tint = MaterialTheme.colors.primary,
                                modifier = Modifier.size(35.dp)
                            )
                        }
                    }
                } else {
                    ComposableCardPoster(item, onClickPosterImage, height)
                }
            }
        }
    }
}

@Composable
fun ComposableMovieRate(item: ClassBaseItemModel?) {
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
                text = String.format("%.1f", item!!.popularity),
                color = Color.White, fontSize = 12.sp,
                modifier = Modifier.padding(start = 4.dp)
            )

        }
    }
}

object KEYS {
    const val K = "next"
}