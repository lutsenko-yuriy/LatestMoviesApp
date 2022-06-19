package com.example.latestmoviesapp.data.image.repos

import com.example.latestmoviesapp.data.general.NetworkCompileTimeArguments
import com.example.latestmoviesapp.data.image.services.ImageConfigurationService
import com.example.latestmoviesapp.domain.images.ImageConfiguration
import javax.inject.Inject

class ImageConfigurationRepoImpl @Inject constructor(
    private val arguments: NetworkCompileTimeArguments,
    private val service: ImageConfigurationService
) : ImageConfigurationRepo {
    override suspend fun getImageConfiguration(): ImageConfiguration {
        val networkImageConfiguration = service.fetchImageConfiguration(arguments.apiKey)

        return ImageConfiguration(baseUrl = networkImageConfiguration.configuration.secureBaseUrl)
    }
}