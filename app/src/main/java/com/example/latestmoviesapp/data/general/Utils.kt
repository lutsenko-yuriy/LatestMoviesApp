package com.example.latestmoviesapp.data.general

import com.example.latestmoviesapp.data.movies.responses.paging.NetworkMovie
import com.example.latestmoviesapp.domain.images.ImageConfiguration
import com.example.latestmoviesapp.domain.movies.MovieShortInfo
import java.text.SimpleDateFormat
import java.util.*

object Utils {

    fun Calendar.formatToNetworkArgument(locale: Locale): String =
        SimpleDateFormat("yyyy-MM-dd", locale).format(this.time)

    fun String.formatToDate(locale: Locale): Calendar =
        Calendar.getInstance().apply {
            time = SimpleDateFormat("yyyy-MM-dd", locale).parse(this@formatToDate)!!
        }


    private fun buildUrlFromPath(configuration: ImageConfiguration, imagePath: String?): String? {
        return if (imagePath != null) {
            "${configuration.baseUrl}original${imagePath}"
        } else {
            null
        }
    }

    fun NetworkMovie.asMovieShortDetail(configuration: ImageConfiguration, locale: Locale): MovieShortInfo {
        return MovieShortInfo(
            id = this.id,
            title = this.title,
            posterUrl = buildUrlFromPath(configuration, this.posterPath),
            releaseDate = this.releaseDate.formatToDate(locale),
            voteAverage = this.voteAverage,
            adult = this.adult,
        )
    }
}