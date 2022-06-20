package com.example.latestmoviesapp.ui.detail

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.latestmoviesapp.R
import com.example.latestmoviesapp.domain.movies.MovieDetailedInfo
import com.example.latestmoviesapp.domain.movies.MovieGenre
import com.example.latestmoviesapp.domain.movies.MovieProductionCompany
import com.example.latestmoviesapp.domain.movies.MovieProductionCountry
import com.example.latestmoviesapp.ui.general.MoviesTopAppBar
import java.util.*

@Composable
fun MovieDetailsScreen(movieDetailsViewModel: MovieDetailsViewModel) {
    val details = movieDetailsViewModel.movieDetails.collectAsState(initial = MovieDetailsScreenState.Loading)

    when (val state = details.value) {
        MovieDetailsScreenState.Error -> ErrorScreen()
        MovieDetailsScreenState.Loading -> LoadingScreen()
        is MovieDetailsScreenState.Success -> MovieDetails(movieDetails = state.movieDetails)
    }
}

@Composable
fun ErrorScreen(modifier: Modifier = Modifier) {
    Scaffold(
        topBar = {
            MoviesTopAppBar(text = stringResource(id = R.string.movie_details_error_title))
        }
    ) {
        Box(modifier = modifier.fillMaxSize()) {
            Text(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(horizontal = 16.dp),
                text = stringResource(
                    id = R.string.movie_details_error_description
                ),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview
@Composable
private fun ErrorScreenPreview() {
    ErrorScreen()
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Scaffold(
        topBar = {
            MoviesTopAppBar(text = stringResource(id = R.string.movie_details_loading_title))
        }
    ) {
        Box(modifier = modifier.fillMaxSize()) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}

@Preview
@Composable
private fun LoadingScreenPreview() {
    LoadingScreen()
}

@Composable
fun MovieDetails(modifier: Modifier = Modifier, movieDetails: MovieDetailedInfo) {
    Scaffold(
        topBar = {
            MoviesTopAppBar(text = movieDetails.title)
        }
    ) {
        Column(
            modifier = modifier.verticalScroll(rememberScrollState())
        ) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(4f / 3),
                contentScale = ContentScale.FillWidth,
                model = movieDetails.posterUrl,
                contentDescription = stringResource(id = R.string.movie_details_content_description, movieDetails.title)
            )

            Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = movieDetails.title,
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            modifier = Modifier.padding(top = 4.dp, bottom = 8.dp),
                            text = "${movieDetails.originalTitle} (${movieDetails.originalLanguage})",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Light,
                            color = Color.Gray
                        )
                    }

                    if (movieDetails.adult) {
                        Text(
                            modifier = Modifier
                                .background(color = Color.Red)
                                .padding(8.dp),
                            text = stringResource(id = R.string.movie_details_adults_only),
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White
                        )
                    }
                }

                Divider()

                if (movieDetails.genres.isNotEmpty()) {
                    Text(
                        modifier = Modifier.padding(vertical = 8.dp),
                        text = movieDetails.genres.joinToString(", ") { it.name },
                        fontWeight = FontWeight.Light,
                        fontSize = 14.sp,
                    )

                    Divider()
                }

                if (!movieDetails.tagline.isNullOrBlank()) {
                    Text(
                        modifier = Modifier.padding(vertical = 8.dp),
                        text = movieDetails.tagline,
                        fontStyle = FontStyle.Italic,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp,
                    )

                    Divider()
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Star,
                        contentDescription = stringResource(id = R.string.movie_details_star_icon_description)
                    )
                    Text(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        text = movieDetails.voteAverage.format(2),
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 24.sp,
                    )

                    val resources = LocalContext.current.resources
                    Text(
                        text = resources.getQuantityString(
                            R.plurals.movie_details_reviews,
                            movieDetails.voteCount,
                            movieDetails.voteCount
                        ),
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.Gray,
                        fontSize = 16.sp,
                    )
                }

                Divider()

                if (!movieDetails.overview.isNullOrBlank()) {
                    Text(
                        modifier = Modifier.padding(vertical = 20.dp),
                        text = movieDetails.overview,
                        fontSize = 14.sp,
                    )

                    Divider()
                }

                if (movieDetails.productionCountries.isNotEmpty()) {
                    Text(
                        modifier = Modifier.padding(vertical = 8.dp),
                        text = stringResource(
                            id = R.string.movie_details_countries,
                            movieDetails.productionCountries.joinToString(", ") { it.name }),
                        fontWeight = FontWeight.Light,
                        fontSize = 14.sp,
                    )
                    Divider()
                }

                if (movieDetails.productionCompanies.isNotEmpty()) {
                    Text(
                        modifier = Modifier.padding(vertical = 8.dp),
                        text = stringResource(
                            id = R.string.movie_details_production_companies,
                            movieDetails.productionCompanies.joinToString(", ") {
                                "${it.name} (${it.originCountry})"
                            }),
                        fontWeight = FontWeight.Light,
                        fontSize = 14.sp,
                    )
                }

                if (movieDetails.homepage.isNotEmpty()) {
                    Divider()

                    val context = LocalContext.current
                    val intent = remember { Intent(Intent.ACTION_VIEW, Uri.parse(movieDetails.homepage)) }
                    ClickableElement(
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .fillMaxWidth(),
                        text = stringResource(id = R.string.movie_details_open_homepage)
                    ) {
                        context.startActivity(intent)
                    }
                }

            }
        }
    }
}

@Composable
fun ClickableElement(modifier: Modifier = Modifier, text: String, onClick: () -> Unit) {
    Row(
        modifier = modifier.clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .weight(1f),
            text = text,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )

        Icon(
            imageVector = Icons.Default.KeyboardArrowRight,
            contentDescription = stringResource(R.string.movie_details_link_icon),
        )
    }
}

@Preview
@Composable
private fun MovieDetailsPreview() {
    MovieDetails(
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

private fun Double.format(digits: Int) = "%.${digits}f".format(this)
