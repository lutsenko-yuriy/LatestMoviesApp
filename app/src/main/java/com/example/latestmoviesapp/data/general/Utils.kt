package com.example.latestmoviesapp.data.general

import com.example.latestmoviesapp.data.movies.responses.details.NetworkGenre
import com.example.latestmoviesapp.data.movies.responses.details.NetworkMovieDetails
import com.example.latestmoviesapp.data.movies.responses.details.NetworkProductionCompany
import com.example.latestmoviesapp.data.movies.responses.details.NetworkProductionCountry
import com.example.latestmoviesapp.data.movies.responses.paging.NetworkMovie
import com.example.latestmoviesapp.data.movies.responses.paging.NetworkPagingMovies
import com.example.latestmoviesapp.domain.images.ImageConfiguration
import com.example.latestmoviesapp.domain.movies.*
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

    fun NetworkPagingMovies.asMovieShortInfoPage(
        configuration: ImageConfiguration,
        runtimeArguments: NetworkRuntimeArguments
    ) = MovieShortInfoPage(
        page = page,
        movies = movies.map { it.asMovieShortDetail(configuration, runtimeArguments.locale) },
        totalPages = totalPages,
        totalResults = totalResults
    )

    private fun NetworkMovie.asMovieShortDetail(configuration: ImageConfiguration, locale: Locale): MovieShortInfo {
        return MovieShortInfo(
            id = this.id,
            title = this.title,
            posterUrl = buildUrlFromPath(configuration, this.posterPath),
            releaseDate = this.releaseDate.formatToDate(locale),
            voteAverage = this.voteAverage,
            adult = this.adult,
        )
    }

    fun NetworkMovieDetails.asMovieDetailedInfo(imageConfiguration: ImageConfiguration, locale: Locale): MovieDetailedInfo {
        return MovieDetailedInfo(
            id = this.id,
            title = this.title,
            originalTitle = this.originalTitle,
            originalLanguage = this.originalLanguage,
            backdropUrl = buildUrlFromPath(imageConfiguration, this.backdropPath),
            tagline = this.tagline,
            releaseDate = this.releaseDate.formatToDate(locale),
            voteAverage = this.voteAverage,
            voteCount = this.voteCount,
            genres = this.genres.map { it.asMovieGenre() },
            overview = this.overview,
            homepage = this.homepage,
            imdbId = this.imdbId,
            productionCompanies = this.productionCompanies.map { it.asProductionCompany(imageConfiguration) },
            productionCountries = this.productionCountries.map { it.asProductionCountry() },
            adult = this.adult,
        )
    }

    private fun NetworkGenre.asMovieGenre(): MovieGenre {
        return MovieGenre(
            id = this.id,
            name = this.name
        )
    }

    private fun NetworkProductionCompany.asProductionCompany(configuration: ImageConfiguration): MovieProductionCompany {
        return MovieProductionCompany(
            id = this.id,
            logoUrl = buildUrlFromPath(configuration, this.logoPath),
            name = this.name,
            originCountry = this.originCountry
        )
    }

    private fun NetworkProductionCountry.asProductionCountry(): MovieProductionCountry {
        return MovieProductionCountry(this.name)
    }

}