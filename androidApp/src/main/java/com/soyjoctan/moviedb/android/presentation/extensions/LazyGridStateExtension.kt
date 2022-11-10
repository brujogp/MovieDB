package com.soyjoctan.moviedb.android.presentation.extensions

import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.*

@Composable
fun LazyGridState.OnBottomReached(loadMore: () -> Unit) {
    // state object which tells us if we should load more
    val shouldLoadMore = remember {
        derivedStateOf {
            val lastVisibleItem = layoutInfo.visibleItemsInfo.lastOrNull()
                ?:
                // list is empty
                // return false here if loadMore should not be invoked if the list is empty
                return@derivedStateOf true

            // Check if last visible item is the last item in the list
            lastVisibleItem.index == layoutInfo.totalItemsCount - 1
        }
    }

    // Convert the state into a cold flow and collect
    LaunchedEffect(shouldLoadMore){
        snapshotFlow { shouldLoadMore.value }
            .collect {
                // if should load more, then invoke loadMore
                if (it) loadMore()
            }
    }
}