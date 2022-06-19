package com.example.latestmoviesapp.data.movies.repos

import com.example.latestmoviesapp.data.general.NetworkCompileTimeArguments
import com.example.latestmoviesapp.data.general.NetworkRuntimeArguments
import com.example.latestmoviesapp.data.general.Utils.asMovieShortInfoPage
import com.example.latestmoviesapp.data.general.Utils.formatToNetworkArgument
import com.example.latestmoviesapp.data.image.repos.ImageConfigurationRepo
import com.example.latestmoviesapp.data.movies.services.LatestMoviesNetworkService
import com.example.latestmoviesapp.domain.movies.MovieShortInfoPage
import javax.inject.Inject

class LatestMoviesRepoImpl @Inject constructor(
    private val compileTimeArguments: NetworkCompileTimeArguments,
    private val runtimeArguments: NetworkRuntimeArguments,
    private val imageConfigurationRepo: ImageConfigurationRepo,
    private val service: LatestMoviesNetworkService
) : LatestMoviesRepo {

    override suspend fun getLatestMovies(page: Int): MovieShortInfoPage {
        val configuration = imageConfigurationRepo.getImageConfiguration()
        val latestMoviesPage = service.fetchLatestMovies(
            apiKey = compileTimeArguments.apiKey,
            locale = runtimeArguments.locale.toLanguageTag(),
            releaseDate = runtimeArguments.latestReleaseDate.formatToNetworkArgument(runtimeArguments.locale),
            order = runtimeArguments.order,
            page = page
        )
        return latestMoviesPage.asMovieShortInfoPage(configuration, runtimeArguments)
    }

}