package com.example.latestmoviesapp.domain.movies

import java.util.*

data class MovieShortInfo(
    val id: Int,
    val title: String,
    val posterUrl: String?,
    val releaseDate: Calendar, // yyyy-MM-dd
    val voteAverage: Int,
    val adult: Boolean
)