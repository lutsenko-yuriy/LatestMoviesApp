package com.example.latestmoviesapp.ui.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.AsyncImage
import com.example.latestmoviesapp.R
import com.example.latestmoviesapp.domain.movies.MovieShortInfo
import com.example.latestmoviesapp.ui.general.MoviesTopAppBar
import java.util.*

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

@Composable
private fun MoviesList(
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

@Composable
fun SingleMovieListItem(
    modifier: Modifier = Modifier,
    movieShortInfo: MovieShortInfo,
    onMovieSelected: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        backgroundColor = MaterialTheme.colors.surface,
        elevation = 4.dp,
        modifier = modifier
            .padding(8.dp)
            .clickable(onClick = onMovieSelected)
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = movieShortInfo.posterUrl,
                contentDescription = stringResource(R.string.movie_list_item_poster_description, movieShortInfo.title),
                modifier = Modifier.size(width = 42.dp, height = 60.dp),
                contentScale = ContentScale.FillHeight
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp)
            ) {
                Text(
                    text = movieShortInfo.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    modifier = Modifier.padding(top = 4.dp),
                    text = stringResource(id = R.string.movie_list_item_avg_review, movieShortInfo.voteAverage),
                    fontSize = 12.sp,
                    fontStyle = FontStyle.Italic,
                    color = Color.Gray
                )
            }

            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = stringResource(R.string.movie_list_item_icon_description, movieShortInfo.title),
            )
        }
    }
}

@Preview
@Composable
private fun SingleMovieListItemPreview() {
    SingleMovieListItem(
        movieShortInfo = MovieShortInfo(
            id = 1,
            title = "Title",
            posterUrl = "https://google.com/original/posterpath.jpg",
            releaseDate = Calendar.getInstance().apply {
                time = Date(2020 - 1900, 5, 5, 0, 0, 0)
            },
            voteAverage = 5,
            adult = true,
        ),
        onMovieSelected = { }
    )
}