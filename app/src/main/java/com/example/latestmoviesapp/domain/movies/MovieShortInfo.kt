package com.example.latestmoviesapp.domain.movies

data class MovieShortInfo(
    val id: Int,
    val title: String,
    val posterUrl: String?,
    val voteAverage: Double
)