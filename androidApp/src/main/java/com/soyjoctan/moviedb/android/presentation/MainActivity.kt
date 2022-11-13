package com.soyjoctan.moviedb.android.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.soyjoctan.moviedb.android.presentation.models.Routes.*
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.soyjoctan.moviedb.android.presentation.screens.*
import com.soyjoctan.moviedb.android.presentation.viewmodels.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    // Remember a SystemUiController
    val systemUiController = rememberSystemUiController()

    SideEffect {
        // Update all of the system bar colors to be transparent, and use
        // dark icons if we're in light theme
        systemUiController.setSystemBarsColor(
            color = Color.Transparent,
            darkIcons = false
        )

        systemUiController.setStatusBarColor(
            color = Color.Transparent,
            darkIcons = false
        )

        systemUiController.setNavigationBarColor(
            color = Color.Transparent
        )

        // setStatusBarColor() and setNavigationBarColor() also exist
    }

    val colors = if (darkTheme) {
        darkColors(
            primary = Color(0xFF37474f),
            primaryVariant = Color(0xFF62727b),
            secondary = Color(0xFF102027)
        )
    } else {
        lightColors(

            primary = Color(0xFFff8a80),
            primaryVariant = Color(0xFFffbcaf),
            secondary = Color(0xFFc85a54),
            onSecondary = Color(0xFF000000)
        )
    }
    val typography = Typography(
        body1 = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp
        )
    )
    val shapes = Shapes(
        small = RoundedCornerShape(4.dp),
        medium = RoundedCornerShape(4.dp),
        large = RoundedCornerShape(0.dp)
    )

    MaterialTheme(
        colors = colors,
        typography = typography,
        shapes = shapes,
        content = content
    )
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MovieViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val controller = rememberNavController()

            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                    NavHost(
                        navController = controller,
                        startDestination = HomeScreen.route
                    ) {
                        composable(HomeScreen.route) {
                            HomeGenres(
                                viewModel = viewModel,
                                onNavigationController = {
                                    controller.navigate(it)
                                }
                            )
                        }
                        composable(ListByDetailGenreScreen.route + "/{genreName}/{genreId}") { bachStackEntry ->
                            val genreName = bachStackEntry.arguments?.getString("genreName")
                            val genreId = bachStackEntry.arguments?.getString("genreId")

                            ListMovieByGenreScreen(
                                genreName = genreName,
                                genreId = genreId!!.toLong(),
                                viewModel = viewModel,
                                onNavigationController = {
                                    controller.navigate(it)
                                }
                            )
                        }
                        composable(CompleteDetailsItemScreen.route) { bachStackEntry ->
                            CompleteDetailsItemScreen(viewModel)
                        }
                    }
                }
            }
        }
    }
}
