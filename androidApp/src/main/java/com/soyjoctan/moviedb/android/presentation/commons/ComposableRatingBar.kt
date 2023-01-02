package com.soyjoctan.moviedb.android.presentation.commons

import android.util.Log
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.StarHalf
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.ceil
import kotlin.math.floor


@Composable
fun RatingBar(
    modifier: Modifier = Modifier,
    rating: Double = 0.0,
    stars: Int = 5,
    starsColor: Color = MaterialTheme.colors.onBackground,
    onChangeValuation: (Double) -> Unit
) {
    val starSize: Dp = 55.dp

    var newRating: Double by remember { mutableStateOf(rating) }

    val filledStars = floor(newRating).toInt()
    val unfilledStars = (stars - ceil(newRating)).toInt()
    val halfStar = !(newRating.rem(1).equals(0.0))
    var completeWidth by remember {
        mutableStateOf(0)
    }

    var starWidth by remember { mutableStateOf(0.dp) }
    val separatorStart = 5.dp

    Row(
        modifier = modifier
            .wrapContentWidth()
            .pointerInput(Unit) {
                detectDragGestures { change: PointerInputChange, _: Offset ->
                    val s = change.position.x.toDp()

                    if (starWidth > 0.dp) {
                        newRating = if (s.toPx() >= completeWidth)
                            stars.toDouble()
                        else if (s.toPx() < 0)
                            0.toDouble()
                        else {
                            var result: Double
                            result = (s / starWidth).toDouble()

                            if (result >= 5)
                                result = 5.0
                            else if (result <= 0)
                                result = 0.0

                            val decimal = String
                                .format("%.1f", result)
                                .split(".")

                            val defResult = if (decimal[1].toInt() <= 5)
                                floor(result)
                            else
                                result

                            defResult
                        }

                        onChangeValuation(newRating)
                    }
                }
            }
            .onGloballyPositioned { layoutCoordinates: LayoutCoordinates ->
                newRating = rating
                completeWidth = layoutCoordinates.size.width
            }
    ) {
        LocalDensity.current.run {
            starWidth = (completeWidth.toDp() / stars) - (separatorStart * 2)
        }

        repeat(filledStars) {
            Icon(
                imageVector = Icons.Outlined.Star,
                contentDescription = null,
                tint = starsColor,
                modifier = Modifier
                    .padding(start = 0.dp, end = 0.dp)
                    .size(starSize)
                    .padding(start = separatorStart, end = separatorStart)
            )
        }

        if (halfStar) {
            Icon(
                imageVector = Icons.Outlined.StarHalf,
                contentDescription = null,
                tint = starsColor,
                modifier = Modifier
                    .padding(start = 0.dp, end = 0.dp)
                    .size(starSize)
                    .padding(start = separatorStart, end = separatorStart)
            )
        }

        repeat(unfilledStars) {
            Icon(
                imageVector = Icons.Outlined.StarOutline,
                contentDescription = null,
                tint = starsColor,
                modifier = Modifier
                    .padding(start = 0.dp, end = 0.dp)
                    .size(starSize)
                    .padding(start = separatorStart, end = separatorStart)
            )
        }
    }
}