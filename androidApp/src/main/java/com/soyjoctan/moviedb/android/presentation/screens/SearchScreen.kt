package com.soyjoctan.moviedb.android.presentation.screens

import android.widget.ProgressBar
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.soyjoctan.moviedb.android.presentation.commons.ComposableDetailsMovieBottomSheet
import com.soyjoctan.moviedb.android.presentation.commons.ComposableVerticalLazyGrid
import com.soyjoctan.moviedb.android.presentation.commons.Subtitle
import com.soyjoctan.moviedb.android.presentation.extensions.OnBottomReached
import com.soyjoctan.moviedb.android.presentation.viewmodels.MovieViewModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)
@Composable
fun SearchScreen(
    viewModel: MovieViewModel,
    onNavigationController: (path: String) -> Unit
) {
    var value by rememberSaveable { mutableStateOf("") }
    val result by viewModel.searchItemsListLiveDataObservable.observeAsState()
    val listState: LazyGridState = rememberLazyGridState()
    var currentPage by rememberSaveable { mutableStateOf(1L) }
    val bottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
    )
    val scope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current

    Column {
        Subtitle("Buscador")

        OutlinedTextField(
            value = value,
            onValueChange = { it: String ->
                value = it
            },
            label = {
                Row {
                    Icon(Icons.Outlined.Search, contentDescription = "Botón para buscar")
                    Text("Busca películas, series o artistas.", Modifier.padding(start = 8.dp))
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),
            keyboardActions = KeyboardActions(onSearch = {
                viewModel.searchItems(query = value, page = 1, currentListItems = null)
                scope.launch {
                    listState.scrollToItem(1)
                }
                focusManager.clearFocus()
            }),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Search
            ),
        )

        if (result?.isNotEmpty() == true) {
            ComposableVerticalLazyGrid(
                result!!,
                viewModel,
                listState,
                bottomSheetState
            )
        } else {
            CircularProgressIndicator()
        }
    }

    listState.OnBottomReached {
        // do on load more
        currentPage += 1
        viewModel.searchItems(
            page = currentPage,
            query = value,
            currentListItems = result
        )
    }

    ComposableDetailsMovieBottomSheet(
        modalState = bottomSheetState,
        viewModel = viewModel,
        onNavigationController
    )
}