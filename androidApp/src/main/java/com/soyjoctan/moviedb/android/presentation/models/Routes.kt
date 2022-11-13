package com.soyjoctan.moviedb.android.presentation.models

sealed class Routes(val route: String) {
    object ListByDetailGenreScreen : Routes("DetailGenre")
    object HomeScreen : Routes("HomeScreen")
    object CompleteDetailsItemScreen : Routes("CompleteDetailsItemScreen")
}
