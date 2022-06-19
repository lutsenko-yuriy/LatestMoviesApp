package com.example.latestmoviesapp.data.movies.repos

import com.example.latestmoviesapp.TestHelperObject.DEFAULT_COMPILE_TIME_ARGUMENTS
import com.example.latestmoviesapp.TestHelperObject.DEFAULT_IMAGE_CONFIGURATION
import com.example.latestmoviesapp.TestHelperObject.DEFAULT_PAGE_TO_FETCH
import com.example.latestmoviesapp.TestHelperObject.DEFAULT_RUNTIME_ARGUMENTS
import com.example.latestmoviesapp.TestHelperObject.LATEST_RELEASE_DATE_AS_STRING
import com.example.latestmoviesapp.TestHelperObject.LOCALE_AS_STRING
import com.example.latestmoviesapp.data.general.NetworkCompileTimeArguments
import com.example.latestmoviesapp.data.general.NetworkRuntimeArguments
import com.example.latestmoviesapp.data.image.repos.ImageConfigurationRepo
import com.example.latestmoviesapp.data.movies.responses.paging.NetworkMovie
import com.example.latestmoviesapp.data.movies.responses.paging.NetworkPagingMovies
import com.example.latestmoviesapp.data.movies.services.LatestMoviesNetworkService
import com.example.latestmoviesapp.domain.movies.MovieShortInfo
import com.example.latestmoviesapp.domain.movies.MovieShortInfoPage
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertTrue
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.*
import java.util.*

@OptIn(ExperimentalCoroutinesApi::class)
class LatestMoviesRepoImplTest {

    private lateinit var repo: LatestMoviesRepoImpl

    private lateinit var dummyCompileTimeArguments: NetworkCompileTimeArguments
    private lateinit var dummyRuntimeArguments: NetworkRuntimeArguments

    @Mock
    private lateinit var imageConfigurationRepo: ImageConfigurationRepo

    @Mock
    private lateinit var service: LatestMoviesNetworkService

    private lateinit var mocksDeinitializer: AutoCloseable

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        dummyCompileTimeArguments = DEFAULT_COMPILE_TIME_ARGUMENTS
        dummyRuntimeArguments = DEFAULT_RUNTIME_ARGUMENTS

        repo = LatestMoviesRepoImpl(
            imageConfigurationRepo, service
        )
    }

    @After
    fun tearDown() {
        mocksDeinitializer.close()
    }

    @Test
    fun getLatestMovies() = runTest {
        whenever(
            service.fetchLatestMovies(
                apiKey = anyString(),
                locale = any(),
                releaseDate = any(),
                order = anyString(),
                page = anyInt()
            )
        ).thenReturn(
            NetworkPagingMovies(
                page = DEFAULT_PAGE_TO_FETCH,
                movies = listOf(
                    NetworkMovie(
                        id = 1,
                        title = "Title",
                        posterPath = "/posterpath.jpg",
                        releaseDate = "2020-05-05",
                        voteAverage = 5,
                        adult = true,
                    )
                ),
                totalPages = 1,
                totalResults = 1
            )
        )

        whenever(
            imageConfigurationRepo.getImageConfiguration()
        ).thenReturn(
            DEFAULT_IMAGE_CONFIGURATION
        )


        val expectedResult = MovieShortInfoPage(
            page = DEFAULT_PAGE_TO_FETCH,
            movies = listOf(
                MovieShortInfo(
                    id = 1,
                    title = "Title",
                    posterUrl = "http://google.com/original/posterpath.jpg",
                    releaseDate = Calendar.getInstance().apply {
                        set(2020, 5, 5, 0, 0, 0)
                    },
                    voteAverage = 5,
                    adult = true,
                )
            ),
            totalPages = 1,
            totalResults = 1
        )
        val actualResult = repo.getLatestMovies(1)

        verify(service, times(1)).fetchLatestMovies(
            apiKey = dummyCompileTimeArguments.apiKey,
            locale = LOCALE_AS_STRING,
            releaseDate = LATEST_RELEASE_DATE_AS_STRING,
            order = dummyRuntimeArguments.order,
            page = DEFAULT_PAGE_TO_FETCH
        )

        assertTrue(expectedResult == actualResult)
    }

}