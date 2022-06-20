package com.example.latestmoviesapp.ui.general

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import com.example.latestmoviesapp.domain.movies.MovieShortInfo

@Composable
fun MoviesList(
    modifier: Modifier = Modifier,
    list: LazyPagingItems<MovieShortInfo>,
    onMovieSelected: (MovieShortInfo) -> Unit
) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        items(items = list) { movieInfo ->
            if (movieInfo != null) {
                SingleMovieListItem(movieShortInfo = movieInfo, onMovieSelected = { onMovieSelected(movieInfo) })
            }
        }
    }
}