package com.example.latestmoviesapp.ui.detail

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.res.stringResource
import com.example.latestmoviesapp.R
import com.example.latestmoviesapp.ui.detail.composables.ErrorView
import com.example.latestmoviesapp.ui.detail.composables.LoadingView
import com.example.latestmoviesapp.ui.detail.composables.MovieDetailsView
import com.example.latestmoviesapp.ui.detail.model.MovieDetailsScreenState
import com.example.latestmoviesapp.ui.detail.model.MovieDetailsViewModel
import com.example.latestmoviesapp.ui.misc.composables.MoviesTopAppBar

@Composable
fun MovieDetailsScreen(
    movieDetailsViewModel: MovieDetailsViewModel,
    onBackPressed: () -> Unit
) {
    val details = movieDetailsViewModel.movieDetails.collectAsState(initial = MovieDetailsScreenState.Loading)

    val state = details.value

    val titleText = when (state) {
        MovieDetailsScreenState.Error -> stringResource(id = R.string.movie_details_error_title)
        MovieDetailsScreenState.Loading -> stringResource(id = R.string.movie_details_loading_title)
        is MovieDetailsScreenState.Success -> state.movieDetails.title
    }

    Scaffold(
        topBar = {
            MoviesTopAppBar(text = titleText, onNavigateBack = onBackPressed)
        },
    ) {
        when (state) {
            MovieDetailsScreenState.Error -> ErrorView()
            MovieDetailsScreenState.Loading -> LoadingView()
            is MovieDetailsScreenState.Success -> MovieDetailsView(movieDetails = state.movieDetails)
        }
    }
}

