package com.example.latestmoviesapp.data.image.repos

import com.example.latestmoviesapp.data.general.NetworkCompileTimeArguments
import com.example.latestmoviesapp.data.image.responses.NetworkConfiguration
import com.example.latestmoviesapp.data.image.responses.NetworkImageConfiguration
import com.example.latestmoviesapp.data.image.services.ImageConfigurationService
import com.example.latestmoviesapp.domain.images.ImageConfiguration
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class ImageConfigurationRepoImplTest {

    private lateinit var repo: ImageConfigurationRepoImpl

    private lateinit var dummyArguments: NetworkCompileTimeArguments

    private lateinit var service: ImageConfigurationService

    @Before
    fun setUp() {
        dummyArguments = NetworkCompileTimeArguments(
            baseUrl = SOME_BASE_NETWORK_URL,
            apiKey = SOME_API_KEY
        )

        service = mock()

        repo = ImageConfigurationRepoImpl(dummyArguments, service)
    }

    @Test
    fun getImageConfiguration() = runTest {
        whenever(service.fetchImageConfiguration(anyString())).thenReturn(
            NetworkImageConfiguration(
                NetworkConfiguration(
                    secureBaseUrl = SOME_SECURE_BASE_URL,
                )
            )
        )

        val expectedConfiguration = ImageConfiguration(SOME_SECURE_BASE_URL)
        val actualConfiguration = repo.getImageConfiguration()

        verify(service, times(1)).fetchImageConfiguration(anyString())
        assert(actualConfiguration == expectedConfiguration)
    }

    companion object {
        const val SOME_BASE_NETWORK_URL = "http://google.com/"
        const val SOME_API_KEY = "api_key"

        const val SOME_SECURE_BASE_URL = "https://google.com/"
    }
}