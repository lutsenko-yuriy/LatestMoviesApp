package com.example.latestmoviesapp.data.movies.repos

import com.example.latestmoviesapp.data.image.repos.ImageConfigurationRepo
import com.example.latestmoviesapp.data.movies.services.LatestMoviesNetworkService
import com.example.latestmoviesapp.domain.movies.MovieShortInfoPage
import javax.inject.Inject

class LatestMoviesRepoImpl @Inject constructor(
    private val imageConfigurationRepo: ImageConfigurationRepo,
    private val service: LatestMoviesNetworkService
) : LatestMoviesRepo {

    override suspend fun getLatestMovies(page: Int): MovieShortInfoPage {
        return MovieShortInfoPage(page = page, movies = emptyList(), totalPages = 12, totalResults = 12134)
    }

}