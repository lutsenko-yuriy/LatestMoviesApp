package com.example.latestmoviesapp.general

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.latestmoviesapp.ui.detail.MovieDetailsScreen
import com.example.latestmoviesapp.ui.detail.MovieDetailsViewModel
import com.example.latestmoviesapp.ui.list.MovieListViewModel
import com.example.latestmoviesapp.ui.list.MoviesListScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            MaterialTheme {
                NavHost(
                    modifier = Modifier.fillMaxSize(),
                    navController = navController,
                    startDestination = "list"
                ) {
                    composable("list") {
                        val movieListViewModel = hiltViewModel<MovieListViewModel>()
                        MoviesListScreen(movieListViewModel = movieListViewModel) { navController.navigate("details/${it.id}") }
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
                        MovieDetailsScreen(movieDetailsViewModel = movieDetailsViewModel)
                    }
                }
            }
        }
    }
}