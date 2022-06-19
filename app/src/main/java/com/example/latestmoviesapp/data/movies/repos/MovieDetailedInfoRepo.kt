package com.example.latestmoviesapp.data.movies.repos

import com.example.latestmoviesapp.domain.movies.MovieDetailedInfo

interface MovieDetailedInfoRepo {
    suspend fun getMovieDetailedInfo(movieId: Int): MovieDetailedInfo
}