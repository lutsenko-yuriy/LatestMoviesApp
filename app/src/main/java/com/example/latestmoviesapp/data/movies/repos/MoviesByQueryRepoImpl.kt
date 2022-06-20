package com.example.latestmoviesapp.data.movies.repos

import com.example.latestmoviesapp.data.general.NetworkCompileTimeArguments
import com.example.latestmoviesapp.data.general.NetworkRuntimeArguments
import com.example.latestmoviesapp.data.general.Utils.asMovieShortInfoPage
import com.example.latestmoviesapp.data.image.repos.ImageConfigurationRepo
import com.example.latestmoviesapp.data.movies.services.MoviesByQueryNetworkService
import com.example.latestmoviesapp.domain.movies.MovieShortInfoPage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MoviesByQueryRepoImpl @Inject constructor(
    private val compileTimeArguments: NetworkCompileTimeArguments,
    private val runtimeArguments: NetworkRuntimeArguments,
    private val imageConfigurationRepo: ImageConfigurationRepo,
    private val service: MoviesByQueryNetworkService
) : MoviesByQueryRepo {

    override suspend fun getMoviesByQuery(query: String, page: Int): MovieShortInfoPage = withContext(Dispatchers.IO) {
        val configuration = imageConfigurationRepo.getImageConfiguration()
        val latestMoviesPage =
            service.fetchMovies(
                apiKey = compileTimeArguments.apiKey,
                locale = runtimeArguments.locale.toLanguageTag(),
                query = query,
                order = runtimeArguments.order,
                page = page
            )

        return@withContext latestMoviesPage.asMovieShortInfoPage(configuration)
    }

}