package com.soyjoctan.moviedb.android.presentation.commons

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.soyjoctan.moviedb.model.genres.Genre
import kotlin.math.round

@Composable
fun TextItem(gender: Genre, modifier: Modifier, onClickGenre: (itemSelected: Genre) -> Unit) {
    gender.name?.let {
        Button(
            shape = RoundedCornerShape(40.dp),
            onClick = { /*TODO*/ },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Gray)
        ) {
            Text(
                text = gender.name!!,
                textAlign = TextAlign.Center,
            color = Color.White
            )
        }
    }
}