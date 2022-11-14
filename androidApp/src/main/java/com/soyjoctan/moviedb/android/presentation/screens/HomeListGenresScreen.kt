package com.soyjoctan.moviedb.android.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.soyjoctan.moviedb.android.presentation.commons.*
import com.soyjoctan.moviedb.android.presentation.models.Routes.*
import com.soyjoctan.moviedb.android.presentation.viewmodels.MovieViewModel
import com.soyjoctan.moviedb.presentation.models.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeGenres(
    viewModel: MovieViewModel,
    onNavigationController: (path: String) -> Unit
) {
    val scaffoldState = rememberScaffoldState()
    val scope: CoroutineScope = rememberCoroutineScope()
    val bottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

    var isGenresLoading by rememberSaveable { mutableStateOf(true) }
    var isTopMoviesLoading by rememberSaveable { mutableStateOf(true) }
    var isUpcomingMoviesLoading by rememberSaveable { mutableStateOf(true) }
    var isPopularTvShowsLoading by rememberSaveable { mutableStateOf(true) }

    val genres: List<GenreModel>? by viewModel.listGenresObservable.observeAsState()
    val topRatedMovies: ArrayList<ClassBaseItemModel>? by viewModel.listTopRatedModelMoviesObservable.observeAsState()
    val upcomingMovies: ArrayList<ClassBaseItemModel>? by viewModel.listUpcomingMoviesModelMoviesObservable.observeAsState()
    val popularTvShows: ArrayList<ClassBaseItemModel>? by viewModel.popularTvShowsListLiveDataObservable.observeAsState()

    makeRequests(viewModel)

    ComposableMainScaffold(
        scaffoldState = scaffoldState,
        titleSection = "Página de inicio",
        coroutineScope = scope,
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

                isTopMoviesLoading = Section("Las mejores películas", topRatedMovies)
                {
                    scope.launch {
                        bottomSheetState.show()
                    }
                    viewModel.itemDetailsSelected.value = it
                }

                isUpcomingMoviesLoading = Section("Próximos estrenos", upcomingMovies)
                {
                    scope.launch {
                        bottomSheetState.show()
                    }
                    viewModel.itemDetailsSelected.value = it
                }

                isPopularTvShowsLoading = Section("Series populares", popularTvShows)
                {
                    onNavigationController(CompleteDetailsItemScreen.route)
                }
            }
        },
        requireTopBar = false,
        onFloatingButtonClick = {

        },
        drawableOnClick = {
            onNavigationController(ListToWatchScreen.route)
        }
    )

    ComposableDetailsMovieBottomSheet(
        modalState = bottomSheetState,
        viewModel = viewModel,
        onNavigationController = onNavigationController
    )
}

fun makeRequests(viewModel: MovieViewModel) {
    viewModel.getGenres()
    viewModel.getTopRatedMovies()
    viewModel.getUpcomingMoviesList()
    viewModel.getPopularTvShows()
}

@Composable
inline fun <reified T : ClassBaseItemModel> Section(
    titleSection: String,
    list: ArrayList<T>?,
    noinline onClickElement: (item: ClassBaseItemModel) -> Unit
): Boolean {
    Subtitle(titleSection)
    if (list != null) {
        ViewCarousel(
            content = list as ArrayList<ClassBaseItemModel>,
            modifier = Modifier,
            onClickPosterImage = onClickElement
        )
        CustomDivider()
        return false
    } else {
        LinearProgressIndicator(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 2.dp)
        )
        return true
    }
}