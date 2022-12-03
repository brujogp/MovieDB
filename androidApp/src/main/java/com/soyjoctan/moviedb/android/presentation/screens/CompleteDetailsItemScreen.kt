package com.soyjoctan.moviedb.android.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import com.soyjoctan.moviedb.android.presentation.commons.ComposableDescriptionTextMovie
import com.soyjoctan.moviedb.android.presentation.commons.ComposableLandscapeBackdropMovie
import com.soyjoctan.moviedb.android.presentation.commons.ComposableMainScaffold
import com.soyjoctan.moviedb.android.presentation.models.Routes
import com.soyjoctan.moviedb.android.presentation.viewmodels.MovieViewModel
import com.soyjoctan.moviedb.presentation.models.ClassBaseItemModel
import com.soyjoctan.moviedb.presentation.models.DetailsMovieModel
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

    movieSelected?.itemId?.let {
        viewModel.searchItemToWatchById(it)
    }

    ComposableMainScaffold(
        scaffoldState = scaffoldState,
        titleSection = "Detalle",
        coroutineScope = scope,
        content = {
            itemSelected?.let { itemSelected ->
                ContentDescription(movieSelected, itemToWatchFromDb, itemSelected)
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
        val (backdropMovie, descriptionMovie, basicInformationContent) = createRefs()

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

        Box(
            modifier = Modifier.constrainAs(basicInformationContent) {
                top.linkTo(backdropMovie.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }.padding(top = 12.dp)
        ) {
            Text(text = "Fecha de estreno: ${itemSelected.releaseDate!!.replace("-","/")}")
        }

        itemSelected.apply {
            ComposableDescriptionTextMovie(
                descriptionMovie = itemSelected.overview ?: "",
                modifier = Modifier
                    .constrainAs(descriptionMovie) {
                        top.linkTo(basicInformationContent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )
        }
    }
}