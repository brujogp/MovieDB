package com.soyjoctan.moviedb.android.presentation.commons

import androidx.compose.foundation.layout.*
import  com.soyjoctan.moviedb.android.presentation.models.Routes.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.soyjoctan.moviedb.android.presentation.models.Routes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun ComposableMainScaffold(
    scaffoldState: ScaffoldState,
    onNavigationController: (path: String) -> Unit,
    titleSection: String?,
    coroutineScope: CoroutineScope,
    content: @Composable (paddingValues: PaddingValues) -> Unit,
    onFloatingButtonClick: (() -> Unit)?,
    drawableOnClick: (() -> Unit)?,
    requireTopBar: Boolean = true
) {
    Scaffold(
        scaffoldState = scaffoldState,
        drawerContent = {
            if (drawableOnClick != null) {
                TextButton(onClick = {
                    drawableOnClick.invoke()
                    coroutineScope.launch {
                        scaffoldState.drawerState.apply {
                            close()
                        }
                    }
                }
                ) {
                    Text(text = "Películas para ver", color = MaterialTheme.colors.onBackground)
                }
            }
        },
        topBar = {
            if (requireTopBar) {
                TopAppBar(
                    title = {
                        Text(text = "$titleSection")
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            coroutineScope.launch {
                                scaffoldState.drawerState.apply {
                                    open()
                                }
                            }
                        }) {
                            Icon(Icons.Filled.Menu, "Menu button")
                        }
                    },
                    contentColor = Color.White,
                    elevation = 16.dp,
                    actions = {
                        IconButton(onClick = { onNavigationController(SearchScreen.route) }) {
                            Icon(Icons.Outlined.Search, contentDescription = "Botón de busqueda")
                        }
                    }

                )
            }
        },
        content = content,
        floatingActionButton = {
            if (onFloatingButtonClick != null) {
                FloatingActionButton(onClick = onFloatingButtonClick) {
                    Icon(Icons.Filled.Add, "Add item", tint = Color.White)
                }
            }
        },
    )
}