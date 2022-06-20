package com.example.latestmoviesapp.ui.detail.composables

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.latestmoviesapp.R
import com.example.latestmoviesapp.domain.movies.MovieDetailedInfo
import com.example.latestmoviesapp.domain.movies.MovieGenre
import com.example.latestmoviesapp.domain.movies.MovieProductionCompany
import com.example.latestmoviesapp.domain.movies.MovieProductionCountry
import com.example.latestmoviesapp.ui.misc.composables.ClickableElement
import com.example.latestmoviesapp.ui.misc.utils.format
import java.lang.Float.max
import java.lang.Float.min
import java.util.*

@Composable
fun MovieDetailsView(movieDetails: MovieDetailedInfo) {
    Box(modifier = Modifier.fillMaxSize()) {
        // A height of the poster on the screen
        val posterHeight = remember { mutableStateOf(0) }
        val scrollState = rememberScrollState()

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
            contentDescription = stringResource(id = R.string.movie_details_content_description, movieDetails.title)
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
                Column(
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp, top = 8.dp)
                ) {
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

                    if (!movieDetails.tagline.isNullOrBlank()) {
                        Text(
                            modifier = Modifier.padding(bottom = 8.dp, top = 20.dp),
                            text = movieDetails.tagline,
                            fontStyle = FontStyle.Italic,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 20.sp,
                        )

                        Divider()
                    }

                    if (!movieDetails.overview.isNullOrBlank()) {
                        Text(
                            modifier = Modifier.padding(vertical = 12.dp),
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
}

@Preview
@Composable
private fun MovieDetailsViewPreview() {
    MovieDetailsView(
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
