package com.soyjoctan.moviedb.android.presentation.commons

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.soyjoctan.moviedb.presentation.models.GenreModel

@Composable
fun TextItem(
    gender: GenreModel,
    modifier: Modifier,
    onClickGenre: (itemSelected: GenreModel) -> Unit
) {
    gender.apply {
        Button(
            shape = RoundedCornerShape(40.dp),
            onClick = { onClickGenre(GenreModel(id = id, name = name)) },
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
        ) {
            Text(
                text = gender.name!!,
                textAlign = TextAlign.Center,
                color = Color.White
            )
        }
    }
}