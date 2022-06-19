package com.example.latestmoviesapp.data.movies.repos

import com.example.latestmoviesapp.domain.movies.MovieShortInfoPage

interface MoviesByQueryRepo {
    suspend fun getMoviesByQuery(query: String, page: Int): MovieShortInfoPage
}