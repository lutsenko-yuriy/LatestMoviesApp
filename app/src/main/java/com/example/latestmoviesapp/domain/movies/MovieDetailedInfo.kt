package com.example.latestmoviesapp.domain.movies

import java.util.*

data class MovieDetailedInfo(
    val id: Int,
    val title: String,
    val originalTitle: String,
    val originalLanguage: String,
    val posterUrl: String?,
    val tagline: String?,
    val releaseDate: Calendar,
    val voteAverage: Double,
    val voteCount: Int,
    val genres: List<MovieGenre>,
    val overview: String?,
    val homepage: String,
    val imdbId: String?,
    val productionCompanies: List<MovieProductionCompany>,
    val productionCountries: List<MovieProductionCountry>,
    val adult: Boolean
)
