package com.example.latestmoviesapp.ui.detail

import android.content.res.Configuration
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.latestmoviesapp.R
import com.example.latestmoviesapp.domain.movies.MovieDetailedInfo
import com.example.latestmoviesapp.domain.movies.MovieGenre
import com.example.latestmoviesapp.domain.movies.MovieProductionCompany
import com.example.latestmoviesapp.domain.movies.MovieProductionCountry
import com.example.latestmoviesapp.ui.detail.composables.*
import com.example.latestmoviesapp.ui.detail.model.MovieDetailsScreenState
import com.example.latestmoviesapp.ui.detail.model.MovieDetailsViewModel
import com.example.latestmoviesapp.ui.misc.composables.MoviesTopAppBar
import java.util.*

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
            is MovieDetailsScreenState.Success -> {
                val configuration = LocalConfiguration.current
                val scrollState = rememberScrollState()

                when (configuration.orientation) {
                    Configuration.ORIENTATION_LANDSCAPE -> {
                        MovieDetailsViewLandscape(movieDetails = state.movieDetails, scrollState = scrollState)
                    }
                    else -> {
                        MovieDetailsViewPortrait(movieDetails = state.movieDetails, scrollState = scrollState)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun MovieDetailsListPreview() {
    MovieDetailsList(
        movieDetails = MovieDetailedInfo(
            id = 1,
            title = "Title",
            originalTitle = "Original Title",
            originalLanguage = "English",
            posterUrl = "https://google.com/original/backdropPath.png",
            tagline = "Tagline",
            releaseDate = Calendar.getInstance().apply {
                time = Date(2020 - 1900, 5, 5, 0, 0, 0)
            },
            voteAverage = 5.5,
            voteCount = 404,
            genres = listOf(
                MovieGenre(
                    id = 1,
                    name = "Some genre"
                ),
                MovieGenre(
                    id = 2,
                    name = "Some other genre"
                )
            ),
            overview = "Overview",
            homepage = "https://google.com",
            imdbId = "imdb_id",
            productionCompanies = listOf(
                MovieProductionCompany(
                    id = 1,
                    logoUrl = "https://google.com/original/logoPath.jpg",
                    name = "Some name",
                    originCountry = "Russia"
                ),
                MovieProductionCompany(
                    id = 2,
                    logoUrl = "https://google.com/original/logoPath2.jpg",
                    name = "Some other name",
                    originCountry = "Germany"
                ),
            ),
            productionCountries = listOf(
                MovieProductionCountry(
                    "Russia"
                ),
                MovieProductionCountry(
                    "Germany"
                )
            ),
            adult = true,
        )

    )
}

