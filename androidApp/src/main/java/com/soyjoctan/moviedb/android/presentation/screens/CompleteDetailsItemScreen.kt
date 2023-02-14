package com.soyjoctan.moviedb.android.presentation.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import com.soyjoctan.moviedb.android.presentation.commons.*
import com.soyjoctan.moviedb.android.presentation.models.Routes
import com.soyjoctan.moviedb.android.presentation.viewmodels.MovieViewModel
import com.soyjoctan.moviedb.presentation.models.*
import com.soyjoctan.moviedb.shared.cache.ItemsLiked
import com.soyjoctan.moviedb.shared.cache.ItemsToWatch
import kotlinx.coroutines.CoroutineScope

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CompleteDetailsItemScreen(
    viewModel: MovieViewModel = viewModel(),
    onNavigationController: (path: String) -> Unit
) {
    val scaffoldState = rememberScaffoldState()
    val scope: CoroutineScope = rememberCoroutineScope()

    val itemSelected: DetailsMovieModel? by viewModel.detailsOfItemSelected.observeAsState()
    val movieSelected: ClassBaseItemModel? by viewModel.itemDetailsSelected.observeAsState()
    val itemToWatchFromDb: ItemsToWatch? by viewModel.searchItemToWatchByIdListLiveDataObservable.observeAsState()
    val likedItemFromDb: ItemsLiked? by viewModel.searchLikedItemsToWatchByIdListLiveDataObservable.observeAsState()
    val credits: MovieCreditsModel? by viewModel.creditsMoviesListLiveDataObservable.observeAsState()
    val detailMovieSelected: DetailsMovieModel? by viewModel.detailsMovieLiveDataObservable.observeAsState()

    viewModel.getCreditsByMovieId(movieSelected?.itemId!!)

    movieSelected?.itemId?.let {
        viewModel.searchItemToWatchById(it)
        viewModel.searchLikedItemById(it)
    }

    ComposableMainScaffold(
        scaffoldState = scaffoldState,
        titleSection = "Detalle",
        coroutineScope = scope,
        content = {
            itemSelected?.let { itemSelected ->
                Column(Modifier.verticalScroll(rememberScrollState())) {

                    ContentDescription(
                        movieSelected,
                        itemToWatchFromDb,
                        likedItemFromDb,
                        itemSelected,
                        credits,
                        onNavigationController,
                        viewModel,
                        detailMovieSelected
                    )

                    CustomDivider()
                    credits?.let {
                        Section(
                            titleSection = "Actores",
                            list = convertItems(credits?.cast as ArrayList<PresentationModelParent>),
                            onClickElement = {},
                            height = 200.dp
                        )
                    }
                }
            }
        },
        onFloatingButtonClick = {

        },
        requireTopBar = false,
        drawableOnClick = { icon ->
            when (icon) {
                Icons.Filled.Home -> {
                    onNavigationController(Routes.HomeScreen.route)
                }
                Icons.Filled.MovieFilter -> {
                    onNavigationController(Routes.ListToWatchScreen.route)
                }
            }
        },
        onNavigationController = onNavigationController
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ContentDescription(
    movieSelected: ClassBaseItemModel?,
    itemToWatchFromDb: ItemsToWatch?,
    likedItemFromDb: ItemsLiked?,
    itemSelected: DetailsMovieModel,
    credits: MovieCreditsModel?,
    onNavigationController: (path: String) -> Unit,
    viewModel: MovieViewModel,
    detailMovieSelected: DetailsMovieModel?,
) {
    ConstraintLayout {
        val (backdropMovie, descriptionMovie, containerBasicInfo, listGenres, metaInfo, ratingBar) = createRefs()

        Box(
            modifier = Modifier.constrainAs(backdropMovie) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        ) {
            ComposableLandscapeBackdropMovie(
                movieSelected = movieSelected,
                onClickToWatchButton = {
                    addItemToWatchList(it, viewModel, detailMovieSelected, movieSelected)
                },
                onClickToLikeButton = {
                    addItemToLikeList(it, viewModel, detailMovieSelected, movieSelected)
                },
                wasMarkedToWatch = itemToWatchFromDb?.itemId == movieSelected?.itemId,
                wasMarkedAsLikedItem = likedItemFromDb?.itemId == movieSelected?.itemId
            )
        }

        Row(modifier = Modifier.constrainAs(containerBasicInfo) {
            top.linkTo(backdropMovie.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }) {
            ComposablePresentationCommonData(
                simpleData = itemSelected.releaseDate!!.replace("-", "/"),
                Icons.Default.CalendarMonth,
                modifier = Modifier
            )

            ComposablePresentationCommonData(
                simpleData = String.format("%.1f", itemSelected.voteAverage),
                icon = Icons.Default.Stars,
                modifier = Modifier
            )

            ComposablePresentationCommonData(
                simpleData = getRevenues(itemSelected.revenue),
                icon = Icons.Default.Paid,
                modifier = Modifier
            )
        }

        if (likedItemFromDb != null) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
                    .constrainAs(ratingBar) {
                        top.linkTo(containerBasicInfo.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            ) {
                RatingBar(
                    modifier = Modifier.fillMaxWidth(),
                    rating = likedItemFromDb!!.rating!!
                ) {
                    viewModel.updateRatingForLikedItem(it, likedItemFromDb!!.itemId)
                }
            }
        }


        Column(
            Modifier
                .constrainAs(metaInfo) {
                    if (likedItemFromDb != null) {
                        top.linkTo(ratingBar.bottom)
                    } else {
                        top.linkTo(containerBasicInfo.bottom)
                    }
                }
                .padding(top = 16.dp)
        ) {
            MetadataItem(
                credits,
                Modifier
            )

            itemSelected.originalTitle?.let {
                Row(modifier = Modifier.padding(start = 16.dp, top = 4.dp)) {
                    Text(text = "Título original: ")
                    Text(
                        text = it,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        ComposableDescriptionTextMovie(
            descriptionMovie = itemSelected.overview ?: "",
            modifier = Modifier
                .constrainAs(descriptionMovie) {
                    top.linkTo(metaInfo.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )

        ComposableGenres(
            genres = itemSelected.genres as ArrayList<GenreModel>,
            modifier = Modifier
                .constrainAs(listGenres) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(descriptionMovie.bottom)
                }
                .padding(top = 16.dp)
        ) { genreClickedId: GenreModel ->
            onNavigationController(Routes.ListByDetailGenreScreen.route + "/${genreClickedId.name}/${genreClickedId.id}")
            genreClickedId.id?.let { it1 ->
                viewModel.getMoviesByGenre(it1, 1, null)
            }
        }
    }
}

@Composable
fun MetadataItem(credits: MovieCreditsModel?, modifier: Modifier) {
    Row(modifier = modifier.padding(start = 16.dp)) {
        Text(text = "Dirigido por: ")
        LazyRow {
            credits?.let {
                items(credits.directors) { item ->
                    if (item.job == "Director")
                        Text(
                            text = item.originalName ?: item.name ?: "",
                            fontWeight = FontWeight.Bold
                        )
                }
            }
        }
    }
}

@Composable
fun ComposableGenres(
    genres: ArrayList<GenreModel>,
    modifier: Modifier,
    onClickGenre: (genre: GenreModel) -> Unit
) {
    ComposableStaggered(
        genres = genres,
        modifier = modifier,
        onClickGenre = onClickGenre,
        cellsRow = 1
    )
}

@Composable
fun ComposablePresentationCommonData(
    simpleData: String,
    icon: ImageVector,
    modifier: Modifier
) {
    Card(
        modifier = modifier
            .padding(top = 12.dp)
            .width(140.dp)
            .padding(start = 8.dp, end = 8.dp),
        backgroundColor = MaterialTheme.colors.primary
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(8.dp)
        ) {
            Icon(
                icon,
                contentDescription = "Calendar icon",
                tint = MaterialTheme.colors.onBackground
            )
            Text(
                text = simpleData,
                fontSize = 14.sp,
                modifier = Modifier.padding(top = 4.dp), color = MaterialTheme.colors.onBackground
            )
        }
    }
}


@Composable
private fun getRevenues(longRevenue: Long?): String {
    var revenue = ""
    // 2 920 357 254
    if (longRevenue.toString().count() > 5) {
        when (longRevenue.toString().count()) {
            5 -> {
                revenue = "${longRevenue.toString().substring(0, 2)} mil"
            }
            6 -> {
                revenue = "${longRevenue.toString().substring(0, 3)} mil"
            }
            7 -> {
                val subString: StringBuilder =
                    StringBuilder(longRevenue.toString().substring(0, 2))
                subString.insert(1, ".")

                revenue = "$subString millones"
            }
            8 -> {
                val subString: StringBuilder =
                    StringBuilder(longRevenue.toString().substring(0, 3))
                subString.insert(2, ".")

                revenue = "$subString millones"
            }
            9 -> {
                val subString: StringBuilder =
                    StringBuilder(longRevenue.toString().substring(0, 4))
                subString.insert(3, ".")

                revenue = "$subString millones"
            }
            10 -> {
                val subString: StringBuilder =
                    StringBuilder(longRevenue.toString().substring(0, 2))
                subString.insert(1, ".")

                revenue = "$subString mil millones"
            }
            11 -> {
                val subString: StringBuilder =
                    StringBuilder(longRevenue.toString().substring(0, 3))
                subString.insert(2, ".")

                revenue = "$subString mil millones"
            }
        }
    } else {
        revenue = longRevenue.toString()
    }

    return revenue
}