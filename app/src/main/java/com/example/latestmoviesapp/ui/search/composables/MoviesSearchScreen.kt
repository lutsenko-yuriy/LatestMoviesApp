package com.example.latestmoviesapp.ui.search.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.latestmoviesapp.R
import com.example.latestmoviesapp.domain.movies.MovieShortInfo
import com.example.latestmoviesapp.ui.misc.composables.MoviesList
import com.example.latestmoviesapp.ui.misc.composables.MoviesTopAppBar
import com.example.latestmoviesapp.ui.misc.composables.SearchView
import com.example.latestmoviesapp.ui.search.models.MovieSearchViewModel

@Composable
fun MoviesSearchScreen(
    movieSearchViewModel: MovieSearchViewModel,
    onMovieSelected: (MovieShortInfo) -> Unit
) {
    Scaffold(
        topBar = {
            MoviesTopAppBar(text = stringResource(id = R.string.movie_search_title))
        }
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            SearchField(movieSearchViewModel)
            MoviesListView(movieSearchViewModel, onMovieSelected)
        }
    }
}

@Composable
private fun SearchField(movieListViewModel: MovieSearchViewModel) {
    val searchText = movieListViewModel.queryString.collectAsState(initial = "")

    SearchView(
        searchText = searchText.value,
        placeholderText = stringResource(id = R.string.movie_search_text),
        onSearchTextChanged = { newQuery -> movieListViewModel.submitNewSearchQuery(newQuery) },
        onClearClick = { movieListViewModel.submitNewSearchQuery("") }
    )
}

@Composable
private fun MoviesListView(
    movieListViewModel: MovieSearchViewModel,
    onMovieSelected: (MovieShortInfo) -> Unit
) {
    val list = movieListViewModel.movies.collectAsLazyPagingItems()

    MoviesList(modifier = Modifier.fillMaxSize(), list = list, onMovieSelected = onMovieSelected)
}
