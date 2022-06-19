package com.example.latestmoviesapp.data.movies.repos

import com.example.latestmoviesapp.domain.movies.MovieShortInfoPage

interface LatestMoviesRepo {
    suspend fun getLatestMovies(page: Int): MovieShortInfoPage
}