package com.soyjoctan.moviedb.android.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.soyjoctan.moviedb.android.presentation.commons.*
import com.soyjoctan.moviedb.android.presentation.models.CarouselModel
import com.soyjoctan.moviedb.android.presentation.models.Routes.*
import com.soyjoctan.moviedb.android.presentation.viewmodels.MovieViewModel
import com.soyjoctan.moviedb.presentation.models.GenreModel
import com.soyjoctan.moviedb.presentation.models.PresentationModelParent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun HomeGenres(viewModel: MovieViewModel, controller: NavHostController) {
    val scaffoldState = rememberScaffoldState()
    val scope: CoroutineScope = rememberCoroutineScope()

    val genres: List<GenreModel>? by viewModel.listGenresObservable.observeAsState()
    val topRatedMovies by viewModel.listTopRatedModelMoviesObservable.observeAsState()
    val upcomingMovies by viewModel.listUpcomingMoviesModelMoviesObservable.observeAsState()

    makeRequests(viewModel)

    Scaffold(
        scaffoldState = scaffoldState,
        drawerContent = {
            Text("Hola mundo")
            Divider()
            Text("Hola mundo")
            Divider()
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Filled.Add, "Add item", tint = Color.White)
            }
        },
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Lista de películas")
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
        },
        content = {
            Column(
                modifier = Modifier
                    .padding(it)
                    .verticalScroll(rememberScrollState())
            ) {

                Subtitle("Géneros")
                genres?.let { genres: List<GenreModel> ->
                    ComposableStaggered(
                        genres = genres,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) { genreClickedId: GenreModel ->
                        controller.navigate(ListByDetailGenreScreen.route + "/${genreClickedId.name}")
                        viewModel.getMoviesByGenre(genreClickedId.id!!)
                    }
                }

                CustomDivider()

                Subtitle("Las mejores películas")
                ViewCarousel(
                    convertorToCarouselModel(topRatedMovies),
                    Modifier
                )
                CustomDivider()

                Subtitle("Próximos estrenos")
                ViewCarousel(
                    convertorToCarouselModel(upcomingMovies),
                    Modifier
                )
            }
        }
    )
}


fun makeRequests(viewModel: MovieViewModel) {
    viewModel.getGenres()
    viewModel.getTopRatedMovies()
    viewModel.getUpcomingMoviesList()
}

inline fun <reified TypeToCarouselModel> convertorToCarouselModel(elements: ArrayList<TypeToCarouselModel>?): ArrayList<CarouselModel> {
    val array = arrayListOf<CarouselModel>()

    elements?.forEach {
        (it as PresentationModelParent)
        array.add(
            CarouselModel(
                movieName = it.movieName,
                posterPathImage = it.posterPathImage,
                popularity = it.popularity,
                movieId = it.movieId
            )
        )
    }

    return array
}