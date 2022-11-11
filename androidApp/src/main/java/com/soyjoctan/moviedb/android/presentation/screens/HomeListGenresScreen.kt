package com.soyjoctan.moviedb.android.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.soyjoctan.moviedb.android.presentation.commons.*
import com.soyjoctan.moviedb.android.presentation.models.CarouselModel
import com.soyjoctan.moviedb.android.presentation.models.Routes.*
import com.soyjoctan.moviedb.android.presentation.viewmodels.MovieViewModel
import com.soyjoctan.moviedb.presentation.models.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeGenres(viewModel: MovieViewModel, onNavigationController: (path: String) -> Unit) {
    val scaffoldState = rememberScaffoldState()
    val scope: CoroutineScope = rememberCoroutineScope()
    val bottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

    var isGenresLoading by rememberSaveable { mutableStateOf(true) }
    var isTopMoviesLoading by rememberSaveable { mutableStateOf(true) }
    var isUpcomingMoviesLoading by rememberSaveable { mutableStateOf(true) }
    var isPopularTvShowsLoading by rememberSaveable { mutableStateOf(true) }

    val genres: List<GenreModel>? by viewModel.listGenresObservable.observeAsState()

    val topRatedMovies: ArrayList<TopRatedModel>? by viewModel.listTopRatedModelMoviesObservable.observeAsState()
    val upcomingMovies: ArrayList<UpcomingMoviesModel>? by viewModel.listUpcomingMoviesModelMoviesObservable.observeAsState()
    val popularTvShows: ArrayList<PopularTvShowsModel>? by viewModel.popularTvShowsListLiveDataObservable.observeAsState()

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
                if (genres != null) {
                    isGenresLoading = false
                    ComposableStaggered(
                        genres = genres!!,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) { genreClickedId: GenreModel ->
                        onNavigationController(ListByDetailGenreScreen.route + "/${genreClickedId.name}/${genreClickedId.id}")

                        genreClickedId.id?.let { it1 ->
                            viewModel.getMoviesByGenre(it1, 1, null)
                        }
                    }

                    CustomDivider()
                } else {
                    LinearProgressIndicator(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 2.dp)
                    )
                }


                Subtitle("Las mejores películas")
                if (topRatedMovies != null) {
                    isTopMoviesLoading = false
                    ViewCarousel(
                        convertorToCarouselModel(topRatedMovies),
                        Modifier
                    ) {
                        scope.launch {
                            bottomSheetState.show()
                        }
                        viewModel.movieDetailsSelected.value = it
                    }
                    CustomDivider()
                } else {
                    LinearProgressIndicator(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 2.dp)
                    )
                }

                Subtitle("Próximos estrenos")
                if (upcomingMovies != null) {
                    isUpcomingMoviesLoading = false
                    ViewCarousel(
                        convertorToCarouselModel(upcomingMovies),
                        Modifier
                    ) {
                        scope.launch {
                            bottomSheetState.show()
                        }
                        viewModel.movieDetailsSelected.value = it
                    }
                } else {
                    LinearProgressIndicator(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 2.dp)
                    )
                }
            }
        }
    )

    ComposableDetailsMovieBottomSheet(
        modalState = bottomSheetState,
        scope = scope,
        viewModel = viewModel
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
                movieId = it.movieId,
                backdropPath = it.backdropPath
            )
        )
    }

    return array
}