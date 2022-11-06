package com.soyjoctan.moviedb.android.iu.commons

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.soyjoctan.moviedb.model.Genre

@Composable
fun TextItem(gender: Genre, modifier: Modifier, onClickGenre: (itemSelected: Genre) -> Unit) {
    gender.name?.let {
        TextButton(
            modifier = modifier
                .fillMaxWidth()
                .padding(start = 16.dp),
            onClick = {
                onClickGenre.invoke(gender)
            }
        ) {
            Text(text = gender.name!!)
        }
    }
}