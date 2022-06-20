package com.example.latestmoviesapp.general

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.latestmoviesapp.domain.movies.MovieShortInfo
import com.example.latestmoviesapp.ui.detail.MovieDetailsScreen
import com.example.latestmoviesapp.ui.detail.model.MovieDetailsViewModel
import com.example.latestmoviesapp.ui.list.composables.MoviesListScreen
import com.example.latestmoviesapp.ui.list.model.MovieListViewModel
import com.example.latestmoviesapp.ui.search.composables.MoviesSearchScreen
import com.example.latestmoviesapp.ui.search.models.MovieSearchViewModel

@Composable
fun MoviesNavHost(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = "list"
    ) {
        val onMovieSelected: (MovieShortInfo) -> Unit = { navController.navigate("details/${it.id}") }
        val onSearchClicked: () -> Unit = { navController.navigate("search") }

        composable("list") {
            val movieListViewModel = hiltViewModel<MovieListViewModel>()
            MoviesListScreen(
                movieListViewModel = movieListViewModel,
                onSearchClicked = onSearchClicked,
                onMovieSelected = onMovieSelected
            )
        }

        composable("search") {
            val movieSearchViewModel = hiltViewModel<MovieSearchViewModel>()
            MoviesSearchScreen(
                movieSearchViewModel = movieSearchViewModel,
                onMovieSelected = onMovieSelected
            )
        }

        composable(
            "details/{movieId}",
            arguments = listOf(navArgument("movieId") { type = NavType.IntType })
        ) {
            val movieDetailsViewModel = hiltViewModel<MovieDetailsViewModel>().apply {
                this.init(
                    it.arguments?.getInt("movieId") ?: -1
                )
            }
            MovieDetailsScreen(
                movieDetailsViewModel = movieDetailsViewModel,
                onBackPressed = { navController.popBackStack() }
            )
        }
    }

}