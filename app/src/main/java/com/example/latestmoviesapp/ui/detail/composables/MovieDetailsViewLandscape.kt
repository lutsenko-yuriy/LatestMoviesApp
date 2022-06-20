package com.example.latestmoviesapp.ui.detail.composables

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.latestmoviesapp.R
import com.example.latestmoviesapp.domain.movies.MovieDetailedInfo

@Composable
fun MovieDetailsViewLandscape(modifier: Modifier = Modifier, movieDetails: MovieDetailedInfo, scrollState: ScrollState) {
    Row(modifier = modifier.fillMaxSize()) {
        AsyncImage(
            modifier = Modifier
                .fillMaxHeight()
                .aspectRatio(7f / 10),
            contentScale = ContentScale.FillWidth,
            model = movieDetails.posterUrl,
            contentDescription = stringResource(id = R.string.movie_details_poster_content_description, movieDetails.title)
        )

        Box(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
                .background(color = Color.White)
                .verticalScroll(scrollState)
        ) {
            MovieDetailsList(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, top = 8.dp),
                movieDetails = movieDetails
            )
        }
    }
}
