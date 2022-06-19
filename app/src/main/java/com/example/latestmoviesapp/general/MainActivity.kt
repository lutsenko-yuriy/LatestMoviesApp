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
import com.example.latestmoviesapp.ui.list.ListViewModel
import com.example.latestmoviesapp.ui.list.MoviesListScreen
import com.example.latestmoviesapp.ui.list.MoviesListScreen.MoviesListScreen
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
                    startDestination = MoviesListScreen.path
                ) {
                    composable(MoviesListScreen.path) {
                        val listViewModel = hiltViewModel<ListViewModel>()
                        MoviesListScreen(listViewModel, navController)
                    }

                    composable(
                        "details/{movieId}",
                        arguments = listOf(navArgument("movieId") { type = NavType.IntType })
                    ) {

                    }
                }
            }
        }
    }
}