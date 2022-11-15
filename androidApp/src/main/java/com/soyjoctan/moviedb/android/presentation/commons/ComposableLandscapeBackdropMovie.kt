package com.soyjoctan.moviedb.android.presentation.commons

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.constraintlayout.compose.ConstraintLayout
import com.soyjoctan.moviedb.presentation.models.ClassBaseItemModel

@Composable
fun ComposableLandscapeBackdropMovie(
    movieSelected: ClassBaseItemModel?,
    wasMarkedToWatch: Boolean,
    onClickToWatchButton: (isMarkedToWatch: Boolean) -> Unit
) {

    var isLiked by rememberSaveable { mutableStateOf(false) }
    var openDialog by remember { mutableStateOf(false) }

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
                if (wasMarkedToWatch) {
                    openDialog = true
                } else {
                    onClickToWatchButton(true)
                }
            },
            modifier = Modifier.constrainAs(iconLike) {
                end.linkTo(parent.end, margin = 8.dp)
            }
        ) {
            Icon(
                if (!wasMarkedToWatch) Icons.Outlined.Add else Icons.Filled.Check,
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

    if (openDialog) {
        NoPaddingAlertDialog(
            onDismissRequest = { openDialog = false },
            confirmButton = {
                TextButton(onClick = { openDialog = false }) {
                    Text(text = "Sí, eliminar", color = MaterialTheme.colors.onBackground)
                }
            },
            dismissButton = {
                TextButton(onClick = { openDialog = false }) {
                    Text(text = "No, cancelar", color = MaterialTheme.colors.onBackground)
                }
            },
            title = {
                Text(
                    text = "Eliminar elemento de tu lista",
                    modifier = Modifier.padding(
                        start = 16.dp,
                        top = 16.dp,
                        end = 16.dp
                    ),
                    fontWeight = FontWeight.Black
                )
            }, text = {
                Text(
                    text = "¿Quires eliminar este elemento de tu lista?",
                    modifier = Modifier.padding(16.dp)
                )
            }
        )
    }
}

@Composable
fun NoPaddingAlertDialog(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    title: @Composable (() -> Unit)? = null,
    text: @Composable (() -> Unit)? = null,
    confirmButton: @Composable () -> Unit,
    dismissButton: @Composable (() -> Unit)? = null,
    shape: Shape = MaterialTheme.shapes.medium,
    backgroundColor: Color = MaterialTheme.colors.surface,
    contentColor: Color = contentColorFor(backgroundColor),
    properties: DialogProperties = DialogProperties()
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = properties
    ) {
        Surface(
            modifier = modifier,
            shape = shape,
            color = backgroundColor,
            contentColor = contentColor
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                title?.let {
                    CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.high) {
                        val textStyle = MaterialTheme.typography.subtitle1
                        ProvideTextStyle(textStyle, it)
                    }
                }
                text?.let {
                    CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.high) {
                        val textStyle = MaterialTheme.typography.subtitle1
                        ProvideTextStyle(textStyle, it)
                    }
                }
                Box(
                    Modifier
                        .fillMaxWidth()
                        .padding(all = 8.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        dismissButton?.invoke()
                        confirmButton()
                    }
                }
            }
        }
    }
}