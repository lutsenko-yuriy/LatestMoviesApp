package com.example.latestmoviesapp.data.image.repos

import com.example.latestmoviesapp.data.general.NetworkCompileTimeArguments
import com.example.latestmoviesapp.data.image.services.ImageConfigurationService
import com.example.latestmoviesapp.domain.images.ImageConfiguration
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ImageConfigurationRepoImpl @Inject constructor(
    private val arguments: NetworkCompileTimeArguments,
    private val service: ImageConfigurationService
) : ImageConfigurationRepo {
    override suspend fun getImageConfiguration(): ImageConfiguration = withContext(Dispatchers.IO) {
        val networkImageConfiguration = service.fetchImageConfiguration(arguments.apiKey)

        return@withContext ImageConfiguration(baseUrl = networkImageConfiguration.configuration.secureBaseUrl)
    }
}