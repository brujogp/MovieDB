package com.soyjoctan.moviedb.android.presentation.commons

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import  com.soyjoctan.moviedb.android.presentation.models.Routes.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    drawableOnClick: ((id: ImageVector) -> Unit)?,
    requireTopBar: Boolean = true
) {
    Scaffold(
        scaffoldState = scaffoldState,
        drawerContent = {
            if (drawableOnClick != null) {
                EntrySection(
                    drawableOnClick, coroutineScope, scaffoldState
                )
                EntryCommunity(
                    drawableOnClick, coroutineScope, scaffoldState
                )
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

@Composable
private fun EntryCommunity(
    drawableOnClick: (icon: ImageVector) -> Unit,
    coroutineScope: CoroutineScope,
    scaffoldState: ScaffoldState
) {
    Column(modifier = Modifier.padding(16.dp)) {
        TextSection("Comunidad")

        ItemList(
            "Redes sociales",
            Icons.Filled.Group,
            drawableOnClick, coroutineScope, scaffoldState
        )
        ItemList(
            "Foro",
            Icons.Filled.Forum,
            drawableOnClick, coroutineScope, scaffoldState
        )
        ItemList(
            "Características",
            Icons.Default.FeaturedPlayList,
            drawableOnClick, coroutineScope, scaffoldState
        )
    }
    Divider(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp),
        color = MaterialTheme.colors.onBackground.copy(alpha = .2f)
    )
}

@Composable
private fun EntrySection(
    drawableOnClick: (icon: ImageVector) -> Unit,
    coroutineScope: CoroutineScope,
    scaffoldState: ScaffoldState
) {
    Column(modifier = Modifier.padding(16.dp)) {
        TextSection("Secciones")

        ItemList(
            "Inicio",
            Icons.Filled.Home,
            drawableOnClick, coroutineScope, scaffoldState
        )
        ItemList(
            "Películas para ver",
            Icons.Filled.MovieFilter,
            drawableOnClick, coroutineScope, scaffoldState
        )
        ItemList(
            "Mi perfil",
            Icons.Filled.Person,
            drawableOnClick, coroutineScope, scaffoldState
        )
    }
    Divider(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp),
        color = MaterialTheme.colors.onBackground.copy(alpha = .2f)
    )
}

fun closeDrawer(coroutineScope: CoroutineScope, scaffoldState: ScaffoldState) {
    coroutineScope.launch {
        scaffoldState.drawerState.apply {
            close()
        }
    }
}

@Composable
fun ItemList(
    nameItem: String,
    icon: ImageVector,
    drawableOnClick: (icon: ImageVector) -> Unit,
    coroutineScope: CoroutineScope,
    scaffoldState: ScaffoldState
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(top = 22.dp)
    )
    {
        Row(modifier = Modifier
            .clickable {
                drawableOnClick.invoke(icon)
                closeDrawer(coroutineScope, scaffoldState)
            }
        ) {
            Icon(
                icon,
                contentDescription = "",
            )
            Text(
                text = nameItem,
                color = MaterialTheme.colors.onBackground,
                modifier = Modifier
                    .padding(start = 20.dp)
            )
        }
    }
}

@Composable
fun TextSection(nameSection: String) {
    Text(
        text = nameSection,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colors.onBackground.copy(alpha = .5f),
        fontSize = 20.sp
    )
}