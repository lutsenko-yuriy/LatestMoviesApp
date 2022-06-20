package com.example.latestmoviesapp.data.image.repos

import com.example.latestmoviesapp.TestHelperObject.DEFAULT_COMPILE_TIME_ARGUMENTS
import com.example.latestmoviesapp.TestHelperObject.DEFAULT_IMAGE_CONFIGURATION
import com.example.latestmoviesapp.TestHelperObject.SOME_SECURE_BASE_URL
import com.example.latestmoviesapp.data.general.NetworkCompileTimeArguments
import com.example.latestmoviesapp.data.image.responses.NetworkConfiguration
import com.example.latestmoviesapp.data.image.responses.NetworkImageConfiguration
import com.example.latestmoviesapp.data.image.services.ImageConfigurationService
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class ImageConfigurationRepoImplTest {

    private lateinit var repo: ImageConfigurationRepoImpl

    private lateinit var dummyArguments: NetworkCompileTimeArguments

    @Mock
    private lateinit var service: ImageConfigurationService

    private lateinit var mocksDeinitializer: AutoCloseable

    @Before
    fun setUp() {
        mocksDeinitializer = MockitoAnnotations.openMocks(this)
        dummyArguments = DEFAULT_COMPILE_TIME_ARGUMENTS

        repo = ImageConfigurationRepoImpl(dummyArguments, service)
    }

    @After
    fun tearDown() {
        mocksDeinitializer.close()
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

        val expectedConfiguration = DEFAULT_IMAGE_CONFIGURATION
        val actualConfiguration = repo.getImageConfiguration()

        verify(service, times(1)).fetchImageConfiguration(anyString())
        assertEquals(expectedConfiguration, actualConfiguration)
    }

    @Test
    fun getImageConfiguration_goesToNetworkOnlyOnce() = runTest {
        whenever(service.fetchImageConfiguration(anyString())).thenReturn(
            NetworkImageConfiguration(
                NetworkConfiguration(
                    secureBaseUrl = SOME_SECURE_BASE_URL,
                )
            )
        )

        val expectedConfiguration = DEFAULT_IMAGE_CONFIGURATION
        val actualConfigurationForFirstTime = repo.getImageConfiguration()
        val actualConfigurationForSecondTime = repo.getImageConfiguration()

        verify(service, times(1)).fetchImageConfiguration(anyString())
        assertEquals(expectedConfiguration, actualConfigurationForFirstTime)
        assertEquals(expectedConfiguration, actualConfigurationForSecondTime)
    }

}