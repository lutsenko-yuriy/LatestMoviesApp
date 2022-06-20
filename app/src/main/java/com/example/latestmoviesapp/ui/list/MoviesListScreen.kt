package com.example.latestmoviesapp.ui.list

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.latestmoviesapp.R
import com.example.latestmoviesapp.domain.movies.MovieShortInfo
import com.example.latestmoviesapp.ui.general.MoviesList
import com.example.latestmoviesapp.ui.general.MoviesTopAppBar

@Composable
fun MoviesListScreen(movieListViewModel: MovieListViewModel, onMovieSelected: (MovieShortInfo) -> Unit) {
    Scaffold(
        topBar = {
            MoviesTopAppBar(text = stringResource(id = R.string.movie_list_title))
        }
    ) {
        val list = movieListViewModel.movies.collectAsLazyPagingItems()

        MoviesList(list = list, onMovieSelected = onMovieSelected)
    }
}