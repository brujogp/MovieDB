package com.soyjoctan.moviedb.android.presentation.commons

import androidx.compose.foundation.background
import androidx.compose.runtime.*
import androidx.compose.foundation.horizontalScroll
import androidx.compose.runtime.key

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import java.util.ArrayList
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayCircleFilled
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.soyjoctan.moviedb.domain.use_cases.GetCreditsByMovieIdUseCase
import com.soyjoctan.moviedb.presentation.models.ClassBaseItemModel


@Composable
fun ViewCarousel(
    content: ArrayList<ClassBaseItemModel>?,
    modifier: Modifier,
    onClickPosterImage: (item: ClassBaseItemModel) -> Unit,
    height: Dp = 140.dp,
    onShowMore: (() -> Unit?)?
) {
    var visibleList: ArrayList<ClassBaseItemModel> by remember { mutableStateOf(arrayListOf()) }

    content?.let {
        if (content.size > 10) {
            visibleList = content.take(10) as ArrayList<ClassBaseItemModel>
            if (content[0].popularity != GetCreditsByMovieIdUseCase.PARAMS.Actors){
                visibleList.add(ClassBaseItemModel(KEYS.K, KEYS.K, 0000.0, null, KEYS.K))
            }
        } else {
            visibleList = content
        }

    }

    Box {
        LazyRow(
            contentPadding = PaddingValues(
                top = 0.dp,
                bottom = 8.dp,
                start = 16.dp,
                end = 16.dp
            ),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(visibleList.toList()) { item: ClassBaseItemModel ->
                key(item.itemId) {
                    if (item.itemName == KEYS.K) {
                        Box(
                            modifier = Modifier.height(height / 2 + 30.dp),
                            contentAlignment = Alignment.BottomCenter
                        ) {
                            Text(
                                text = "Ver lista completa",
                                color = MaterialTheme.colors.onBackground,
                                modifier = Modifier.padding(bottom = 55.dp)
                            )
                            IconButton(
                                onClick = { onShowMore?.invoke() }
                            ) {
                                Icon(
                                    Icons.Default.PlayCircleFilled,
                                    contentDescription = "",
                                    tint = MaterialTheme.colors.primary,
                                    modifier = Modifier.size(35.dp)
                                )
                            }
                        }
                    } else if (item.popularity == GetCreditsByMovieIdUseCase.PARAMS.Actors) {
                        ComposableCircleCardPoster(item, onClickPosterImage)
                    } else {
                        ComposableCardPoster(item, onClickPosterImage, height)
                    }
                }
            }
        }
    }
}

@Composable
fun ComposableMovieRate(item: ClassBaseItemModel?) {
    if (item?.popularity != GetCreditsByMovieIdUseCase.PARAMS.Actors) {
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
}

object KEYS {
    const val K = "next"
}