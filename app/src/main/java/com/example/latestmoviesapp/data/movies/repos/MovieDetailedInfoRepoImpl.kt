package com.example.latestmoviesapp.data.movies.repos

import com.example.latestmoviesapp.data.general.NetworkCompileTimeArguments
import com.example.latestmoviesapp.data.general.NetworkRuntimeArguments
import com.example.latestmoviesapp.data.general.Utils.asMovieDetailedInfo
import com.example.latestmoviesapp.data.image.repos.ImageConfigurationRepo
import com.example.latestmoviesapp.data.movies.services.MovieDetailsNetworkService
import com.example.latestmoviesapp.domain.movies.MovieDetailedInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieDetailedInfoRepoImpl @Inject constructor(
    private val compileTimeArguments: NetworkCompileTimeArguments,
    private val runtimeArguments: NetworkRuntimeArguments,
    private val imageConfigurationRepo: ImageConfigurationRepo,
    private val service: MovieDetailsNetworkService
) : MovieDetailedInfoRepo {
    override suspend fun getMovieDetailedInfo(movieId: Int): MovieDetailedInfo = withContext(Dispatchers.IO) {
        val imageConfiguration = imageConfigurationRepo.getImageConfiguration()
        return@withContext service.fetchMovieDetails(compileTimeArguments.apiKey, runtimeArguments.locale.toLanguageTag(), movieId)
            .asMovieDetailedInfo(imageConfiguration, runtimeArguments.locale)
    }
}