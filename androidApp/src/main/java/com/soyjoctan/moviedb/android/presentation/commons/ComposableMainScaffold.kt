package com.soyjoctan.moviedb.android.presentation.commons

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun ComposableMainScaffold(
    scaffoldState: ScaffoldState,
    titleSection: String?,
    coroutineScope: CoroutineScope,
    content: @Composable (paddingValues: PaddingValues) -> Unit,
    onFloatingButtonClick: (() -> Unit)?,
    requireTopBar: Boolean = true
) {
    Scaffold(
        scaffoldState = scaffoldState,
        drawerContent = {
            Text("Hola mundo")
            Divider()
            Text("Hola mundo")
            Divider()
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
                    elevation = 16.dp
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
        }
    )
}