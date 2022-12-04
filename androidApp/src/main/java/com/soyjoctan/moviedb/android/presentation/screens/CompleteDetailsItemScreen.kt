package com.soyjoctan.moviedb.android.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Paid
import androidx.compose.material.icons.filled.Stars
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import com.soyjoctan.moviedb.android.presentation.commons.*
import com.soyjoctan.moviedb.android.presentation.models.Routes
import com.soyjoctan.moviedb.android.presentation.viewmodels.MovieViewModel
import com.soyjoctan.moviedb.presentation.models.*
import com.soyjoctan.moviedb.shared.cache.ItemsToWatch
import kotlinx.coroutines.CoroutineScope

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
    val credits: MovieCreditsModel? by viewModel.creditsMoviesListLiveDataObservable.observeAsState()

    viewModel.getCreditsByMovieId(movieSelected?.itemId!!)

    movieSelected?.itemId?.let {
        viewModel.searchItemToWatchById(it)
    }

    ComposableMainScaffold(
        scaffoldState = scaffoldState,
        titleSection = "Detalle",
        coroutineScope = scope,
        content = {
            itemSelected?.let { itemSelected ->
                Column(Modifier.verticalScroll(rememberScrollState())) {
                    ContentDescription(movieSelected, itemToWatchFromDb, itemSelected)

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
        drawableOnClick = {
            onNavigationController(Routes.ListToWatchScreen.route)
        },
        onNavigationController = onNavigationController
    )
}

@Composable
fun ContentDescription(
    movieSelected: ClassBaseItemModel?,
    itemToWatchFromDb: ItemsToWatch?,
    itemSelected: DetailsMovieModel
) {
    ConstraintLayout {
        val (backdropMovie, descriptionMovie, containerBasicInfo) = createRefs()

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

                },
                wasMarkedToWatch = itemToWatchFromDb?.itemId == movieSelected?.itemId,
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
        ComposableDescriptionTextMovie(
            descriptionMovie = itemSelected.overview ?: "",
            modifier = Modifier
                .constrainAs(descriptionMovie) {
                    top.linkTo(containerBasicInfo.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )
    }
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