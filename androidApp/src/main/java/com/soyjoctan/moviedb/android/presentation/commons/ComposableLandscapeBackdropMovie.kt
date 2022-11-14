package com.soyjoctan.moviedb.android.presentation.commons

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.soyjoctan.moviedb.android.presentation.models.CarouselModel

@Composable
fun ComposableLandscapeBackdropMovie(
    movieSelected: CarouselModel?,
    onClickToWatchButton: (isMarkedToWatch: Boolean) -> Unit
) {

    var isLiked by rememberSaveable { mutableStateOf(false) }
    var isInWishList by rememberSaveable { mutableStateOf(false) }

    LandscapeImage(stringPath = movieSelected?.backdropPath)
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
    ) {
        val (iconBookmark, text, iconLike) = createRefs()

        IconButton(
            onClick = {
                isLiked = !isLiked
            },
            modifier = Modifier.constrainAs(iconBookmark) {
                top.linkTo(iconLike.bottom)
                end.linkTo(parent.end, margin = 8.dp)
            }
        ) {
            Icon(
                if (isLiked) Icons.Filled.ThumbUp else Icons.Outlined.ThumbUp,
                contentDescription = "Me gusta",
                tint = Color.White
            )
        }

        IconButton(
            onClick = {
                isInWishList = !isInWishList
                onClickToWatchButton(isInWishList)
            },
            modifier = Modifier.constrainAs(iconLike) {
                end.linkTo(parent.end, margin = 8.dp)
            }
        ) {
            Icon(
                if (isInWishList) Icons.Filled.Check else Icons.Outlined.Add,
                contentDescription = "Añadir a la wish list",
                tint = Color.White
            )
        }

        Text(
            text = movieSelected?.itemName ?: "",
            color = Color.White,
            modifier = Modifier.constrainAs(text) {
                bottom.linkTo(parent.bottom, margin = 16.dp)
                start.linkTo(parent.start, margin = 16.dp)
            },
            fontWeight = FontWeight.ExtraBold,
            fontSize = 32.sp,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}