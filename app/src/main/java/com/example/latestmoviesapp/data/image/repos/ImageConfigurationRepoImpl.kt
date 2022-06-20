package com.example.latestmoviesapp.data.image.repos

import com.example.latestmoviesapp.data.general.NetworkCompileTimeArguments
import com.example.latestmoviesapp.data.image.services.ImageConfigurationService
import com.example.latestmoviesapp.domain.images.ImageConfiguration
import kotlinx.coroutines.*
import java.util.concurrent.atomic.AtomicReference
import javax.inject.Inject

class ImageConfigurationRepoImpl @Inject constructor(
    private val arguments: NetworkCompileTimeArguments,
    private val service: ImageConfigurationService
) : ImageConfigurationRepo {

    private val localImageConfigurationReference = AtomicReference<Deferred<ImageConfiguration>>()

    override suspend fun getImageConfiguration(): ImageConfiguration = withContext(Dispatchers.IO) {
        val currentImageConfiguration = localImageConfigurationReference.get() ?: synchronized(this) {
            val newImageConfiguration =
                async(start = CoroutineStart.LAZY) { ImageConfiguration(service.fetchImageConfiguration(arguments.apiKey).configuration.secureBaseUrl) }
            localImageConfigurationReference.compareAndSet(null, newImageConfiguration)
            newImageConfiguration.start()
            newImageConfiguration
        }

        return@withContext currentImageConfiguration.await()
    }
}