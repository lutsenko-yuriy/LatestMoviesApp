package com.example.latestmoviesapp.ui.detail.model

import com.example.latestmoviesapp.domain.movies.MovieDetailedInfo

sealed class MovieDetailsScreenState {
    object Loading : MovieDetailsScreenState()

    data class Success(val movieDetails: MovieDetailedInfo): MovieDetailsScreenState()

    object Error : MovieDetailsScreenState()
}
