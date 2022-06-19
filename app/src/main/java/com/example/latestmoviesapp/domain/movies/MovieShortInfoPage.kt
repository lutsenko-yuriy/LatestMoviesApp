package com.example.latestmoviesapp.domain.movies

data class MovieShortInfoPage(
    val page: Int,
    val movies: List<MovieShortInfo>,
    val totalPages: Int,
    val totalResults: Int
)