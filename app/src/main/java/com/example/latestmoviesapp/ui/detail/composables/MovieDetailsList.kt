package com.example.latestmoviesapp.ui.detail.composables

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.latestmoviesapp.R
import com.example.latestmoviesapp.domain.movies.MovieDetailedInfo
import com.example.latestmoviesapp.ui.misc.composables.ClickableElement
import com.example.latestmoviesapp.ui.misc.utils.format

@Composable
fun MovieDetailsList(modifier: Modifier = Modifier, movieDetails: MovieDetailedInfo) {
    Column(
        modifier = modifier
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