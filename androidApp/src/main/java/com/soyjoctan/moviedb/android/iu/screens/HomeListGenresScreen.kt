package com.soyjoctan.moviedb.android.iu.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.soyjoctan.moviedb.android.data.MovieViewModel
import com.soyjoctan.moviedb.android.iu.commons.TextItem
import com.soyjoctan.moviedb.model.Genre
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun HomeGenres(
    viewModel: MovieViewModel,
    genres: List<Genre>?,
    onClickGenre: (genre: Genre) -> Unit,
    scope: CoroutineScope
) {
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        drawerContent = {
            Text("Hola mundo")
            Divider()
            Text("Hola mundo")
            Divider()
            Text("Hola mundo")
            Divider()
            Text("Hola mundo")
            Divider()
            Text("Hola mundo")
            Divider()
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Filled.Add, "Add item")
            }
        },
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Géneros")
                },
                navigationIcon = {
                    IconButton(onClick = {
                        scope.launch {
                            scaffoldState.drawerState.apply {
                                open()
                            }
                        }
                    }) {
                        Icon(Icons.Filled.Menu, "Menu button")
                    }
                },
                contentColor = Color.White,
                elevation = 16.dp
            )
        }
    ) { it ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (genres.isNullOrEmpty()) {
                viewModel.getGenres()
            }

            genres?.let {
                ViewListGenres(
                    genres = it,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    onClickGenre = onClickGenre
                )
            }
        }
    }
}

@Composable
fun ViewListGenres(genres: List<Genre>, modifier: Modifier, onClickGenre: (genre: Genre) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        items(genres) { item ->
            TextItem(gender = item, modifier = modifier, onClickGenre = onClickGenre)
            Spacer(
                modifier = Modifier
                    .background(Color.Gray)
                    .fillMaxWidth()
                    .height(1.dp)
            )
        }
    }
}
