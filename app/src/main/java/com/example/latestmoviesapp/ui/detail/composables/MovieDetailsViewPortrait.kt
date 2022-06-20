package com.example.latestmoviesapp.ui.detail.composables

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.latestmoviesapp.R
import com.example.latestmoviesapp.domain.movies.MovieDetailedInfo
import java.lang.Float.max
import java.lang.Float.min

@Composable
fun MovieDetailsViewPortrait(modifier: Modifier = Modifier, movieDetails: MovieDetailedInfo, scrollState: ScrollState) {
    Box(modifier = modifier.fillMaxSize()) {
        // A height of the poster on the screen
        val posterHeight = remember { mutableStateOf(0) }

        val posterHeightInDp = with(LocalDensity.current) { posterHeight.value.toDp() }

        val alpha = if (posterHeight.value == 0) {
            0f
        } else {
            max(0f, min(1f, 1 - scrollState.value.toFloat() / posterHeight.value))
        }

        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(7f / 10)
                .alpha(alpha)
                .onGloballyPositioned {
                    posterHeight.value = it.size.height
                },
            contentScale = ContentScale.FillWidth,
            model = movieDetails.posterUrl,
            contentDescription = stringResource(id = R.string.movie_details_poster_content_description, movieDetails.title)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState)
        ) {

            // Add height of the poster to the scrollable movie info
            Box(
                modifier = Modifier
                    .height(posterHeightInDp)
                    .fillMaxWidth()
            )

            Divider()

            Box(
                modifier = Modifier
                    .background(color = Color.White)
                    .fillMaxSize()
            ) {
                MovieDetailsList(
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp, top = 8.dp),
                    movieDetails = movieDetails
                )
            }
        }
    }
}
