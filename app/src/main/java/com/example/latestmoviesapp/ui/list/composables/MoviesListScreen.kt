package com.example.latestmoviesapp.ui.list.composables

import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.latestmoviesapp.R
import com.example.latestmoviesapp.domain.movies.MovieShortInfo
import com.example.latestmoviesapp.ui.list.model.MovieListViewModel
import com.example.latestmoviesapp.ui.misc.composables.MoviesList
import com.example.latestmoviesapp.ui.misc.composables.MoviesTopAppBar

@Composable
fun MoviesListScreen(
    movieListViewModel: MovieListViewModel,
    onSearchClicked: () -> Unit,
    onMovieSelected: (MovieShortInfo) -> Unit
) {
    Scaffold(
        topBar = {
            MoviesTopAppBar(text = stringResource(id = R.string.movie_list_title))
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onSearchClicked) {
                Icon(
                    Icons.Default.Search,
                    contentDescription = stringResource(id = R.string.movie_search_button_description)
                )
            }
        }
    ) {
        val list = movieListViewModel.movies.collectAsLazyPagingItems()

        MoviesList(list = list, onMovieSelected = onMovieSelected)
    }
}